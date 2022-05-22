import cv2
import numpy as np

#Cogemos como imagen el afilapuntas

afilapuntas = cv2.imread("afilapuntas.jpg")

_,binarizada = cv2.threshold(afilapuntas,130,255,cv2.THRESH_BINARY)
_,binar_inver = cv2.threshold(afilapuntas,130,255,cv2.THRESH_BINARY_INV)
_,truncada = cv2.threshold(afilapuntas,130,255,cv2.THRESH_TRUNC)
_,trunc_inver = cv2.threshold(afilapuntas,130,255,cv2.THRESH_TOZERO)

cv2.imshow('Original',afilapuntas)
cv2.imshow('Binarizada',binarizada)
cv2.imshow('Binarizada invertida',binar_inver)
cv2.imshow('Truncada',truncada)
cv2.imshow('Truncada invertida',trunc_inver)

cv2.waitKey(0)
cv2.destroyAllWindows()