#!/bin/bash

echo "🚀 Lancement des services avec reconstruction..."
docker-compose up --build &

# Récupère le PID pour savoir quand c'est prêt
COMPOSE_PID=$!

# Attente que le frontend soit accessible
echo "⏳ Attente que le frontend soit disponible..."
until curl -s -o /dev/null -w "%{http_code}" http://localhost:8080 | grep -q "200\|302"; do
  echo "⏳ En attente que le front réponde avec un code HTTP 200 ou 302..."
  sleep 3
done

echo "✅ Frontend prêt ! Accédez au site : http://localhost:8080"
wait $COMPOSE_PID