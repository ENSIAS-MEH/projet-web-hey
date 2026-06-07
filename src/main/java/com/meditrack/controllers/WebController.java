package com.meditrack.controllers;

import com.meditrack.dao.*;
import com.meditrack.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Controller
public class WebController {

    @Autowired
    private LoginAttemptService loginAttemptService;

    private final UserDAO userDAO = new UserDAOImpl();
    private final VitalsDAO vitalsDAO = new VitalsDAOImpl();
    private final AppointmentDAO appointmentDAO = new AppointmentDAOImpl();
    private final AlertDAO alertDAO = new AlertDAOImpl();
    private final DoctorDAO doctorDAO = new DoctorDAOImpl();

    @GetMapping("/login")
    public String afficherPageLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String traiterConnexion(@RequestParam String email,
                                   @RequestParam String password,
                                   HttpSession session,
                                   Model model) {

        if (loginAttemptService.estBloque(email)) {
            model.addAttribute("erreur", "Compte temporairement verrouillé après plusieurs tentatives.");
            return "login";
        }

        String resultat = userDAO.authentifierEtGetRole(email, password);

        if (resultat != null) {
            loginAttemptService.succes(email);

            String[] parts = resultat.split(":");
            String role = parts[0];
            int userId = Integer.parseInt(parts[1]);

            session.setAttribute("userId", userId);
            session.setAttribute("role", role);

            if ("MEDECIN".equals(role)) {
                return "redirect:/doctor/dashboard";
            } else {
                return "redirect:/dashboard";
            }
        } else {
            loginAttemptService.echec(email);
            model.addAttribute("erreur", "Identifiants incorrects.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String seDeconnecter(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String afficherPageInscription() {
        return "register";
    }

    @PostMapping("/register")
    public String traiterInscription(@RequestParam String role,
                                     @RequestParam String nom,
                                     @RequestParam String prenom,
                                     @RequestParam String email,
                                     @RequestParam String password,
                                     @RequestParam(required = false) String sexe,
                                     @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateNaissance,
                                     @RequestParam(required = false) String specialite,
                                     Model model) {

        User nouvelUtilisateur = null;

        if ("PATIENT".equals(role)) {
            if (sexe == null || sexe.isEmpty() || dateNaissance == null) {
                model.addAttribute("erreur", "Erreur : Sexe et date de naissance obligatoires pour un patient.");
                return "register";
            }
            nouvelUtilisateur = new Patient(0, email, password, nom, prenom, sexe, dateNaissance);
        } else if ("MEDECIN".equals(role)) {
            if (specialite == null || specialite.isEmpty()) {
                model.addAttribute("erreur", "Erreur : Spécialité obligatoire pour un médecin.");
                return "register";
            }
            nouvelUtilisateur = new Doctor(0, email, password, nom, prenom, specialite);
        } else {
            model.addAttribute("erreur", "Erreur : Rôle invalide.");
            return "register";
        }

        if (nouvelUtilisateur == null) {
            model.addAttribute("erreur", "Erreur : Impossible de créer le compte.");
            return "register";
        }

        String resultat = userDAO.creerCompte(nouvelUtilisateur);

        if ("SUCCESS".equals(resultat)) {
            return "redirect:/login";
        } else {
            model.addAttribute("erreur", resultat);
            return "register";
        }
    }

    @GetMapping("/dashboard")
    public String afficherDashboard(HttpSession session, Model model) {
        Integer patientId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (patientId == null || !"PATIENT".equals(role)) {
            return "redirect:/login";
        }
        
        List<Doctor> listeMedecins = doctorDAO.getAllDoctors();
        model.addAttribute("listeMedecins", listeMedecins);

        model.addAttribute("historique", vitalsDAO.findByPatient(patientId));
        model.addAttribute("mesRdv", appointmentDAO.getRendezVousPatient(patientId));
        model.addAttribute("alertes", alertDAO.getAlertesPourPatient(patientId));
        model.addAttribute("medecins", doctorDAO.getAllDoctors());
        
        return "dashboard";
    }

    @PostMapping("/vitals")
    public String enregistrerConstante(@RequestParam String tension,
                                       @RequestParam double poids,
                                       @RequestParam double glycemie,
                                       HttpSession session,
                                       Model model) {

        Integer patientId = (Integer) session.getAttribute("userId");
        if (patientId == null || !"PATIENT".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        User patientUser = userDAO.findById(patientId);
        String patientNomComplet = "Patient ID " + patientId;
        
        if (patientUser instanceof Patient) {
            Patient p = (Patient) patientUser;
            patientNomComplet = p.getPrenom() + " " + p.getNom();
        }
        final String nomPatient = patientNomComplet;

        Vitals nouvelleMesure = new Vitals(0, new Date(), tension, poids, glycemie, patientId);
        vitalsDAO.save(nouvelleMesure);

        List<Integer> doctorIdsList = appointmentDAO.getDoctorIdsForPatient(patientId);
        System.out.println("DEBUG - patientId : " + patientId);
        System.out.println("DEBUG - doctorIds : " + doctorIdsList);
        System.out.println("DEBUG - glycemie : " + glycemie);

        Set<Integer> doctorIds = new HashSet<>(doctorIdsList);

        for (int doctorId : doctorIds) {
            System.out.println("DEBUG - Envoi alerte au docteur ID : " + doctorId);

            if (glycemie > 1.26) {
                alertDAO.createAlert(doctorId, "⚠️ Glycémie critique (" + glycemie + " g/L) pour " + nomPatient, patientId);
            }
            try {
                String[] parts = tension.split("/");
                if (parts.length == 2) {
                    int systolique = Integer.parseInt(parts[0].trim());
                    int diastolique = Integer.parseInt(parts[1].trim());
                    if (systolique > 14 || diastolique > 9) {
                        alertDAO.createAlert(doctorId, "⚠️ Tension élevée (" + tension + " cmHg) pour le patient ID " + nomPatient, patientId);
                    }
                }
            } catch (NumberFormatException e) {
            }
        }

        model.addAttribute("msgConstantes", "✅ Données enregistrées avec succès !");
        return afficherDashboard(session, model);
    }

    @PostMapping("/appointments")
    public String reserverRendezVous(@RequestParam int doctorId,
                                     @RequestParam String dateHeure,
                                     HttpSession session,
                                     Model model) {

        Integer patientId = (Integer) session.getAttribute("userId");
        if (patientId == null || !"PATIENT".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }

        Date dateRdv = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            dateRdv = formatter.parse(dateHeure);
        } catch (Exception e) {
            model.addAttribute("msgRdvError", "❌ Format de date invalide.");
            return afficherDashboard(session, model);
        }

        boolean estLibre = appointmentDAO.checkDisponibilite(doctorId, dateRdv);

        if (estLibre) {
            appointmentDAO.saveAppointment(patientId, doctorId, dateRdv, "PLANIFIE");
            model.addAttribute("msgRdvSuccess", "✅ Réservation confirmée !");
        } else {
            model.addAttribute("msgRdvError", "❌ Ce créneau est déjà pris. Choisissez une autre heure.");
        }

        return afficherDashboard(session, model);
    }

    @PostMapping("/appointments/cancel")
    public String annulerRendezVous(@RequestParam int appointmentId,
                                    HttpSession session,
                                    Model model) {
        
        Integer patientId = (Integer) session.getAttribute("userId");
        if (patientId == null || !"PATIENT".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        appointmentDAO.updateStatut(appointmentId, "ANNULE");
        model.addAttribute("msgRdvSuccess", "✅ Rendez-vous annulé.");
        return afficherDashboard(session, model);
    }

    @GetMapping("/doctor/dashboard")
    public String afficherDoctorDashboard(HttpSession session, Model model) {
        Integer doctorId = (Integer) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (doctorId == null || !"MEDECIN".equals(role)) {
            return "redirect:/login";
        }

        Doctor doctorConnecte = doctorDAO.getDoctorById(doctorId);
        model.addAttribute("doctorConnecte", doctorConnecte);

        model.addAttribute("alertes", alertDAO.getAlertesNonLues(doctorId));
        model.addAttribute("mesRdv", appointmentDAO.getRendezVousMedecin(doctorId));
        
        return "doctor-dashboard";
    }

    @PostMapping("/doctor/appointments/update")
    public String modifierStatutRdv(@RequestParam int appointmentId,
                                    @RequestParam String statut,
                                    HttpSession session) {
        
        if (!"MEDECIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        appointmentDAO.updateStatut(appointmentId, statut);
        return "redirect:/doctor/dashboard";
    }

    @PostMapping("/doctor/alerts/read")
    public String marquerAlerteLue(@RequestParam int alertId, HttpSession session) {
        
        if (!"MEDECIN".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        
        alertDAO.marquerCommeLue(alertId);
        return "redirect:/doctor/dashboard";
    }
}
