import numpy as np
import cv2
cap = cv2.VideoCapture(0)
#Aqui cargas la imagen en jpg que tu quieras
imagen=cv2.imread('Sahara.jpg')

#Iniciamos ciclo que se cerrara al pulsar 'q'
while(True):
    ret, frame = cap.read()
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)
#Aplicamos filtro de luminosidad y creamos mascaras filtro y filtroInvertido
    ret,filtro=cv2.threshold(gray,150,255,cv2.THRESH_BINARY)
    filtroInvertido=cv2.bitwise_not(filtro)
#Recortamos video y foto con ambas mascaras
    videoRecortado = cv2.bitwise_and(frame,frame,mask = filtroInvertido)
    fotoRecortada = cv2.bitwise_and(imagen,imagen,mask = filtro)
#Sumamos las imagenes recortadas y las mostramos por ventana
    videoFinal=cv2.add(videoRecortado,fotoRecortada)
    cv2.imshow('Filtros en video',videoFinal)
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break
cap.release()
cv2.destroyAllWindows()