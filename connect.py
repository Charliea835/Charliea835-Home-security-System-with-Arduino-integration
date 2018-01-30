import numpy as np
import cv2
import mysql.connector
from mysql.connector import errorcode
from time import sleep
import serial
import sys
from PIL import Image
import base64
import cStringIO
import PIL.Image
from PIL import ImageFile

# Obtain connection string information from the portal
config = {
  'host':'oursystem.mysql.database.azure.com',
  'user':'project835@oursystem',
  'password':'Homesecure835',
  'database':'projectdb'
}
i=0
arduinoData=[] #declare list #this will store the arduino sensors data 
# Construct connection string
try:
   conn = mysql.connector.connect(**config)
   print("Connection established")
except mysql.connector.Error as err:
  if err.errno == errorcode.ER_ACCESS_DENIED_ERROR:
    print("Something is wrong with the user name or password")     #connect to database 
  elif err.errno == errorcode.ER_BAD_DB_ERROR:
	print("Database does not exist")
  else:
    print(err)
else:
   cursor = conn.cursor()
   ser = serial.Serial('COM4', 9600) # Establish the connection on a specific port
      
cap = cv2.VideoCapture(0)
cursor.execute("CREATE TABLE if not exists Alarm (ID INT not null AUTO_INCREMENT,time TIMESTAMP not null ,Primary key(ID));")
while i<5:
   data=ser.readline().strip()
   print data
   i=i+1
   if int(data)<60:
		frame = cap.read()[1]
		pic = str(frame)
		cv2.imwrite(filename='img.jpg', img=frame)
		arduinoData.append(data)
   elif int(data)==10000:
		cursor.execute("INSERT INTO Alarm (time) VALUES(now())")
		print("Inserted",cursor.rowcount,"row(s) of data.")
		i=i-1
   else:
     	arduinoData.append(data)
#Drop previous table of same name if one exists
cursor.execute("DROP TABLE IF EXISTS SensorData;")
cursor.execute("drop table if exists camera")
print("Finished dropping table (if existed).")

cursor.execute("CREATE TABLE if not exists Camera (img LONGBLOB)") #image is stored as longblob to give more memory to image so it doesent truncate and get greyscaled edges
# Create table
cursor.execute("CREATE TABLE SensorData (ID INT NOT NULL AUTO_INCREMENT,value VARCHAR(40),PRIMARY KEY(ID));")
print("Finished creating Sensor table.")


# Insert some data into table
cursor.executemany("INSERT INTO SensorData (value) VALUES (%s), (%s), (%s), (%s), (%s);",[arduinoData])
print("Inserted",cursor.rowcount,"row(s) of data.")

ImageFile.LOAD_TRUNCATED_IMAGES = True
image = Image.open('C:\\year 3 S1\\Project work\\thirdYearProjectFiles\\img.jpg')
blob_value = open('C:\\year 3 S1\\Project work\\thirdYearProjectFiles\\img.jpg', 'rb').read()
sql = 'INSERT INTO camera(img) VALUES(%s)'    
args = (blob_value, )
cursor.execute(sql,args)
# Cleanup
ser.close()
conn.commit()
cursor.close()
cap.release()
conn.close()
print("Done.")

