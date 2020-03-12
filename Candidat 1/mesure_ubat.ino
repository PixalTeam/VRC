/*
 * Code d'exemple pour la fonction analogRead().
 */

// Fonction setup(), appelée au démarrage de la carte Arduino
void setup() {

  // Initialise la communication avec le PC
  Serial.begin(9600);
}

// Fonction loop(), appelée continuellement en boucle tant que la carte Arduino est alimentée
void loop() {
  
  // Mesure la tension sur la broche A0
  int valeur = analogRead(A0);
  
  // Transforme la mesure (nombre entier) en tension via un produit en croix
  float tension = valeur * (5.0 / 1023.0);
  
  // Envoi la mesure au PC pour affichage et attends 250ms
  Serial.println(tension);
  delay(250);
}
