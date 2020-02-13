/*Réception donnée HF*/
#include <SoftwareSerial.h>
SoftwareSerial xbee(2, 3);

void setup()
{
    xbee.begin(9600);                 
    Serial.begin(9600);                     
}

void loop()
{
  if(xbee.available()) {
    while(xbee.available())  {
      Serial.write(xbee.read());
      }
    }
}
