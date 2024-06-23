#!/bin/bash

# Verificar si se proporcionó el tag como parámetro
if [ -z "$1" ]; then
  echo "Por favor ingrese el tag. Ejemplo: $0 dev"
  exit 1
fi

# Compilar el proyecto usando Maven
echo "Compilando el proyecto..."
mvn clean package

if [ $? -ne 0 ]; then
  echo "La compilación falló. Abortando."
  exit 1
fi

# Encontrar el archivo JAR/WAR generado
BUILD_FILE=$(find target -name "*.jar" -o -name "*.war" | head -n 1)

if [ -z "$BUILD_FILE" ]; then
  echo "No se encontró el archivo JAR/WAR en el directorio target. Abortando."
  exit 1
fi

# Limpiar el directorio target, dejando solo el archivo JAR/WAR
echo "Limpiando el directorio target..."
find target -type f -not -name "$(basename $BUILD_FILE)" -delete
find target -type d -empty -delete

# Asegurarse de que el archivo JAR/WAR esté en el directorio raíz del proyecto
mv "$BUILD_FILE" target/

# Construir la imagen Docker
echo "Construyendo la imagen Docker..."
docker build -t ghcr.io/dtorresm25/nomina-v2:$1 .

if [ $? -ne 0 ]; then
  echo "La construcción de la imagen Docker falló. Abortando."
  exit 1
fi

echo "Proceso completado exitosamente."
