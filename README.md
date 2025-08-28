# EventHub — Mikroservis Portföy Projesi

EventHub; Gateway, Discovery (Eureka), Config Server ve ayrı domain servisleri (user, event, inventory, order, payment, notification, reporting) içeren, dev/prod ayrımı olan bir demo monorepo projesidir.

## Çalışma Biçimi
- **Branching:** `feature/<kısa-özet>` → Pull Request → `master`
- **Kod İnceleme:** Tüm değişiklikler PR ile; küçük bile olsa en az 1 onay.
- **Sürümleme (Prod):** Git tag formatı `vX.Y.Z` (ör. `v1.0.0`)
- **Commit Formatı (Conventional Commits):**
  - `feat:` yeni özellik
  - `fix:` hata düzeltme
  - `docs:` dokümantasyon
  - `chore:` yapılandırma/araç/güncelleme
  - `refactor:`, `test:`, `perf:`, `ci:`, `build:`
  - örn: `feat(order): create order endpoint`

## Depo Düzeni (Özet)
- `gateway/`, `discovery/`, `config-server/`
- `services/`: `user-service/`, `event-service/`, `inventory-service/`, `order-service/`, `payment-service/`, `notification-service/`, `reporting-service/`
- `libs/common/`
- `ops/`: `docker-compose.dev.yml`, `k8s/base|dev|prod`, `grafana`, `prometheus`, `loki-stack`
- `.github/workflows/` (CI/CD akışları)

## Ortamlar
- **Dev:** Docker Compose ile altyapı (Postgres, Kafka, Keycloak, Prometheus, Grafana)
- **Prod:** Kubernetes + Kustomize (overlay: `dev`, `prod`)
- **Config:** Ayrı repo: `eventhub-config` (branch: `dev` ve `prod`)


