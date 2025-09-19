#!/bin/bash

# Codes couleur
GREEN="\033[0;32m"
YELLOW="\033[1;33m"
BLUE="\033[0;34m"
RESET="\033[0m"  # remet la couleur par défaut

echo -e "${BLUE}🚀 Lancement des services avec reconstruction...${RESET}"
docker-compose up --build &

COMPOSE_PID=$!

echo -e "${YELLOW}⏳ Attente que le frontend soit disponible...${RESET}"
until curl -s -o /dev/null -w "%{http_code}" http://localhost:8080 | grep -q "200\|302"; do
  echo -e "${YELLOW}⏳ En attente que le front réponde avec un code HTTP 200 ou 302...${RESET}"
  sleep 3
done

echo -e "${GREEN}✅ Frontend prêt ! Accédez au site : http://localhost:8080${RESET}"
wait $COMPOSE_PID