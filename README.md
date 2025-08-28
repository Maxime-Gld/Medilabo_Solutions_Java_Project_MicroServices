# Qu'est-ce que le Green Code ?

Le **Green Code** (ou "code vert" en français) désigne l’ensemble des pratiques et techniques visant à réduire l’empreinte environnementale du développement logiciel.  
Plus concrètement, il s’agit de concevoir, développer et maintenir des applications informatiques de façon plus économe en énergie et en ressources matérielles.

---

## 🎯 Objectifs du Green Code

- Réduire la consommation d'énergie des logiciels (processeur, mémoire, stockage, bande passante…)
- Limiter les émissions de CO₂ liées à l’utilisation et au développement des solutions numériques
- Optimiser la durée de vie des matériels informatiques en évitant la surconsommation et en favorisant la réutilisation

---

## 🛠️ Principes du Green Code

- **Optimisation des algorithmes** : privilégier les algorithmes moins gourmands en ressources.
- **Nettoyage du code** : supprimer le code inutile, éviter les redondances.
- **Sobriété fonctionnelle** : développer uniquement ce qui est nécessaire, éviter la surcomplexité.
- **Choix technologiques adaptés** : utiliser des frameworks, langages et architectures consommant peu de ressources.
- **Test et mesure** : surveiller l’impact environnemental du logiciel grâce à des outils de mesure de performance et d'émissions carbone.

---

## 📌 Exemples concrets

- Réduire la fréquence de rafraîchissement d’une page web pour limiter les requêtes serveur.
- Compresser les images et ressources pour diminuer le poids des données à transférer.
- Éviter les calculs inutiles dans une boucle ou une fonction.
- Utiliser des conteneurs légers (ex. Alpine Linux) pour réduire la consommation mémoire et disque.
- Déployer des systèmes **serverless** pour n’utiliser les ressources que lorsque cela est nécessaire.


-  Optimisation de requête par des Query ou des DTO :
```Java
@Repository
public interface PatientRepository  extends JpaRepository<PatientEntity, Integer> {

    Optional<PatientEntity> findByNameAndLastname(String name, String lastname);

    // utilisation de @Query pour sélectionner uniquement les champs nécessaires pour la projection
    @Query("SELECT new com.medilabo.solutions.patient.dto.projection.RiskPatientProjection(p.id, p.birthdate, p.gender) FROM PatientEntity p WHERE p.id = :id")
    Optional<RiskPatientProjection> findRiskPatientById(@Param("id") int id);
}
```

---

## 🌍 Pourquoi c’est important ?

- Le secteur numérique représente déjà **plus de 4% des émissions mondiales de gaz à effet de serre (GES)**.  
- La démarche Green Code s’inscrit dans une logique de **responsabilité sociétale** et d’**innovation durable**.


