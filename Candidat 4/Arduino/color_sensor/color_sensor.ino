const int s0 = 6;
const int s1 = 5;
const int s2 = 4;
const int s3 = 3;
const int out = 2;
 
//Couleurs variables
int rouge = 0;
int vert = 0;
int bleu = 0;
 
void setup()
{
  pinMode(s0, OUTPUT);
  pinMode(s1, OUTPUT);
  pinMode(s2, OUTPUT);
  pinMode(s3, OUTPUT);
  pinMode(out, INPUT);

  Serial.begin(9600);
  //sencibilité= S0 >L S1 >H =2% - S0 >H S1 >L =20%- S0 >H S1 >H =100%:
  digitalWrite(s0, HIGH);
  digitalWrite(s1, HIGH);
}
 
void loop()
{
  //Détecte la couleur:
  color();
  //Affiche les valeurs dans le moniteur de série:
 
  //Vérifie que la couleur rouge a été détectée
  if (rouge < bleu && rouge < vert && rouge < 100)
  {
    Serial.println("Rouge");
  }
 
  //Vérifie que la couleur bleu a été détectée
  else if (bleu < rouge && bleu < vert && bleu < 1000)
  {
    Serial.println("Bleu");
  }
 
  //Vérifie que la couleur vert a été détectée
  else if (vert < rouge && vert < bleu)
  {
    Serial.println("Vert");
  }
}
 
void color()
{
  //Routine qui lit la valeur des couleurs
  digitalWrite(s2, LOW);
  digitalWrite(s3, LOW);
  //count OUT, prouge, rouge
  rouge = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);
  digitalWrite(s3, HIGH);
  //count OUT, pbleu, bleu
  bleu = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);
  digitalWrite(s2, HIGH);
  //count OUT, pvert, vert
  vert = pulseIn(out, digitalRead(out) == HIGH ? LOW : HIGH);
}
