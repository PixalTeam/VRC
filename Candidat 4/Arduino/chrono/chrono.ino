int count = 0;
unsigned long milli, milli2 = 0, liste[100] = {};

void setup()
{
  Serial.begin(9600);
}
  
enum EtatCourant {
   Initialisation,
   AttenteReponse,
} etat = Initialisation;
  
void loop()
{
  switch ( etat ) {
     case Initialisation:
        etat = AttenteReponse;
     break;
  
     case AttenteReponse:
         switch ( Serial.read() ) {
             case 'C':                
                //millis() = Le nombre de millisecondes depuis que le programme est lancé
                milli2 = milli2 + milli;
                //milli2 = Le somme totale des millisecondes depuis que le programme est lancé
                milli = millis() - milli2;
                //milli = Le nombre de millisecondes entre chaque clicks
                liste[count] = milli;
                //liste[] = La liste des milliseconds
                                
                Serial.println(liste[count]);
                count++;
                etat = AttenteReponse;
             break;
             case -1:     // rien reçu il faut attendre
             case '\n':   // ignorer terminateur
              break;
             default:
                Serial.println("Erreur saisie");
             break;
         }
     break;
    }
}
