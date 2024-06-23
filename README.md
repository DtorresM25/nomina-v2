# nomina-konecta

# Configuraciones

Clone el repositorio y abralo en el IDE de su preferencia, debe contar con la verion 17 del JDK de Java

## Crear imagen de docker

```bash
# Conceda permisos de lectura y escritura al script para construir imagen de docker de la aplicacion
./build.sh tag_name

# Login con docker y github registry
docker login ghcr.io

# push imagen del docker al repositorio
docker push ghcr.io/dtorresm25/nomina-v2:tag_name
```

## desplegar como contendor de docker con docker-compose

Cree el siguiente archivo docker-compose.yml

```yml
# desplegar contenedor con la aplicacion
docker run -d -p 8080:8080 nomina:tag_name
```

Ejecute el siguiente comando para correr el contendor de docker

```bash
docker compose up -d
```

## Documentacion de endpoint (swagger)

```bash
# acceda a la documentacion generada por swagger del end-point para calcular la fecha de pago de la nomina (puede probar la funcionalidad directamente desde aqui)
http://localhost:8080/docs

```
