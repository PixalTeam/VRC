#include <Wire.h>
#include <rgb_lcd.h>

rgb_lcd lcd;

const int colorR = 0;
const int colorG = 255;
const int colorB = 255;

void setup() {
  
  Serial.begin(9600);
    lcd.begin(16, 2);
    
    lcd.setRGB(colorR, colorG, colorB);
    
    // Print a message to the LCD.
    lcd.print("acceleration :");

    delay(1000);
}

void loop() {
     lcd.setCursor(0, 1);
    // print the number of seconds since reset:
    

    
     

    Serial.print(Serial.read());
delay(100);
}
