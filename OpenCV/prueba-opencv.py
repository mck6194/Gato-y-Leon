import cv2
# Cargamos la imagen del disco duro
imagen = cv2.imread("atleti.jpg")

cv2.imshow("prueba", imagen)
cv2.waitKey(1000)
cv2.destroyAllWindows()