# GitLab CI/CD - Guía de Aprendizaje
### 1. Pipeline Analytics
- Tiempo promedio de ejecución
- Tasa de éxito/fallo
- Cuellos de botella

### 2. Quality Metrics
- Cobertura de código
- Deuda técnica
- Vulnerabilidades detectadas

### 3. Deploy Metrics
- Frequency de deploys
- Lead time
- MTTR (Mean Time To Recovery)

## Integración con Otras Herramientas

### 1. SonarQube Integration
```yaml
include:
  - template: Code-Quality.gitlab-ci.yml
```

### 2. Docker Registry
```yaml
variables:
  DOCKER_DRIVER: overlay2
  DOCKER_TLS_CERTDIR: "/certs"
```

### 3. Docker Swarm Deploy
```yaml
deploy_swarm:
  image: docker:20.10.16
  script:
    - docker stack deploy -c docker-compose.swarm.yml tech-stack
```

## 📚 Recursos de Aprendizaje y Referencias

### Documentación Oficial de GitLab

#### GitLab CI/CD Core
- **📖 [GitLab CI/CD Documentation](https://docs.gitlab.com/ee/ci/)** - Documentación completa oficial
- **🎓 [GitLab CI/CD Quick Start](https://docs.gitlab.com/ee/ci/quick_start/)** - Guía de inicio rápido
- **📖 [Pipeline Configuration Reference](https://docs.gitlab.com/ee/ci/yaml/)** - Referencia completa de YAML
- **🎯 [GitLab Learning](https://about.gitlab.com/learn/)** - Centro de aprendizaje

#### GitLab CI/CD Examples
- **📖 [GitLab CI Templates](https://gitlab.com/gitlab-org/gitlab/-/tree/master/lib/gitlab/ci/templates)** - Templates oficiales
- **🎓 [Auto DevOps](https://docs.gitlab.com/ee/topics/autodevops/)** - Configuración automática
- **📖 [GitLab CI Examples](https://docs.gitlab.com/ee/ci/examples/)** - Ejemplos por tecnología

### Libros y Cursos

#### Libros Recomendados
- **"The DevOps Handbook" - Gene Kim, Jez Humble, Patrick Debois**
  - **📚 [Comprar en Amazon](https://www.amazon.com/DevOps-Handbook-World-Class-Reliability-Organizations/dp/1942788002)**
  - **Contenido**: Principios DevOps, CI/CD, cultura

- **"Continuous Delivery" - Jez Humble, David Farley**
  - **📚 [Versión Digital](https://www.oreilly.com/library/view/continuous-delivery/9780321670250/)**
  - **Contenido**: Pipelines, deployment, automatización

#### Cursos Online
- **GitLab CI/CD Fundamentals** (Udemy)
- **DevOps with GitLab CI/CD** (Coursera)
- **GitLab CI/CD Master Class** (Pluralsight)
- **Continuous Integration and Deployment** (LinkedIn Learning)

### Comunidades y Blogs

#### Comunidades
- **GitLab Community Forum**: [Foros oficiales](https://forum.gitlab.com/)
- **Reddit**: r/gitlab, r/devops, r/cicd
- **Stack Overflow**: Etiquetas `gitlab-ci`, `gitlab-ci-pipelines`
- **GitLab Slack Community**

#### Blogs Técnicos
- **GitLab Blog**: [Actualizaciones y tutoriales](https://about.gitlab.com/blog/)
- **DevOps.com**: [Artículos sobre CI/CD](https://devops.com/)
- **Martin Fowler**: [Continuous Integration](https://martinfowler.com/articles/continuousIntegration.html)

### Herramientas Complementarias

#### CI/CD Alternativas
- **Jenkins**: [Plataforma clásica](https://www.jenkins.io/)
- **GitHub Actions**: [CI/CD de GitHub](https://github.com/features/actions)
- **CircleCI**: [CI/CD cloud-native](https://circleci.com/)
- **Azure DevOps**: [Suite completa de Microsoft](https://azure.microsoft.com/en-us/products/devops/)

#### Testing y Calidad
- **SonarQube**: [Análisis de calidad](https://www.sonarsource.com/products/sonarqube/)
- **OWASP ZAP**: [Testing de seguridad](https://www.zaproxy.org/)
- **Selenium**: [Testing de UI](https://www.selenium.dev/)
- **JUnit/TestNG**: [Testing unitario](https://junit.org/)

## 🎯 Rutas de Aprendizaje

### Nivel Principiante (1-2 semanas)
1. **Conceptos básicos** de CI/CD
2. **Primer pipeline** simple
3. **Jobs y stages** básicos
4. **Variables y secrets**

### Nivel Intermedio (2-4 semanas)
1. **Pipelines complejos** con múltiples stages
2. **Testing automatizado** integrado
3. **Despliegues** a diferentes entornos
4. **Cache y artifacts** optimizados

### Nivel Avanzado (4-6 semanas)
1. **GitOps** con GitLab
2. **Infrastructure as Code**
3. **Security scanning** integrado
4. **Auto-scaling** y optimización

## 🏆 Mejores Prácticas Implementadas

### Pipeline Design
- ✅ **Stages lógicos** (build, test, deploy)
- ✅ **Jobs paralelos** para optimización
- ✅ **Cache inteligente** de dependencias
- ✅ **Artifacts** apropiados

### Seguridad
- ✅ **Secrets management** con variables protegidas
- ✅ **RBAC** (Role-Based Access Control)
- ✅ **Security scanning** integrado
- ✅ **Dependency scanning**

### Calidad
- ✅ **Quality gates** con SonarQube
- ✅ **Coverage reports** automáticos
- ✅ **Performance testing** integrado
- ✅ **Compliance checks**

## 🔧 Configuración Avanzada

### Pipeline con Matrices
```yaml
test:
  stage: test
  script: mvn test
  parallel:
    matrix:
      - JAVA_VERSION: [11, 17]
        MAVEN_VERSION: [3.8, 3.9]
```

### Dynamic Pipelines
```yaml
include:
  - local: .gitlab/ci/dynamic-pipelines.yml
    rules:
      - changes:
          - src/**

generate-pipeline:
  stage: .pre
  script: generate-dynamic-pipeline.sh
```

### GitOps Workflow
```yaml
deploy:
  stage: deploy
  script:
    - git clone https://gitlab.com/infrastructure.git
    - docker stack deploy -c docker-compose.swarm.yml tech-stack
  environment:
    name: production
    url: https://myapp.com
```

## 🚀 Próximos Pasos

### Inmediatos
1. **Implementar GitOps** completamente
2. **Agregar security scanning** avanzado
3. **Configurar auto-scaling**
4. **Implementar blue-green deployments**

### A Largo Plazo
1. **Multi-cloud deployments**
2. **AI/ML en pipelines**
3. **Edge computing** CI/CD
4. **Serverless** pipelines

## 📊 Métricas de Éxito

### Pipeline Performance
- **Build Time**: Tiempo promedio de ejecución
- **Success Rate**: Porcentaje de pipelines exitosos
- **MTTR**: Mean Time To Recovery
- **Deployment Frequency**: Frecuencia de despliegues

### Calidad de Código
- **Coverage**: Cobertura de tests
- **Quality Gate**: Compliance con estándares
- **Security Issues**: Vulnerabilidades detectadas
- **Performance**: Benchmarks de rendimiento

## 🎓 Certificaciones Recomendadas

- **GitLab Certified Associate**
  - [📋 Información](https://about.gitlab.com/certification/)
- **Certified Kubernetes Administrator (CKA)**
  - [📋 CNCF](https://www.cncf.io/certification/cka/) - *Para aprendizaje avanzado*
- **AWS DevOps Engineer Professional**
  - [📋 AWS Certification](https://aws.amazon.com/certification/)

## 📞 Comunidad y Soporte

- **💬 [GitLab Forum](https://forum.gitlab.com/)** - Comunidad oficial
- **🐙 [GitLab Issues](https://gitlab.com/gitlab-org/gitlab/-/issues)** - Reportes de bugs
- **📧 [GitLab Support](https://about.gitlab.com/support/)** - Soporte oficial
- **🎯 [GitLab Meetups](https://about.gitlab.com/community/meetups/)** - Eventos locales

## 🔄 Comparación con Alternativas

| Característica | GitLab CI/CD | Jenkins | GitHub Actions | CircleCI |
|----------------|-------------|---------|----------------|----------|
| **Integración** | ✅ Nativa | ❌ Separada | ✅ Nativa | ❌ Separada |
| **Escalabilidad** | ✅ Auto-scaling | ⚠️ Configurable | ✅ Auto-scaling | ✅ Auto-scaling |
| **GitOps** | ✅ Nativo | ⚠️ Plugins | ⚠️ Limitado | ❌ |
| **Security** | ✅ Integrado | ⚠️ Plugins | ✅ Integrado | ✅ Integrado |
| **Costo** | ⚠️ Freemium | ✅ Gratuito | ✅ Incluido | ⚠️ Freemium |
| **Kubernetes** | ✅ Nativo | ⚠️ Plugins | ⚠️ Limitado | ⚠️ Limitado |

## 📋 Checklist de Implementación

### Pipeline Básico
- [x] Stages definidos (build, test, deploy)
- [x] Jobs configurados
- [x] Variables de entorno
- [x] Cache de dependencias

### Pipeline Avanzado
- [x] Testing automatizado
- [x] Quality gates
- [x] Security scanning
- [ ] Performance testing
- [ ] Load testing
- [ ] Chaos engineering

### Operaciones
- [x] Multi-entorno deployment
- [x] Rollback automático
- [ ] Blue-green deployment
- [ ] Canary deployment
- [ ] Feature flags

---

*GitLab CI/CD representa el estado del arte en integración y despliegue continuo, combinando potencia, facilidad de uso y integración nativa con Git.*

## Próximos Pasos

1. Implementar pipeline básico
2. Agregar quality gates
3. Configurar deploys automáticos
4. Implementar rollback strategies
5. Explorar GitLab Pages para documentación

## ¿Qué es GitLab CI/CD?

GitLab CI/CD es una herramienta integrada para Integración Continua (CI) y Despliegue Continuo (CD) que automatiza el proceso de construcción, testing y despliegue de aplicaciones.

## Conceptos Clave

### 1. Pipeline
Conjunto de trabajos organizados en etapas que se ejecutan automáticamente cuando se hace push al repositorio.

### 2. Stages (Etapas)
- **Build**: Compilación del código
- **Test**: Ejecución de pruebas
- **Quality**: Análisis de calidad (SonarQube)
- **Package**: Empaquetado (Docker)
- **Deploy**: Despliegue a diferentes entornos

### 3. Jobs (Trabajos)
Tareas individuales que se ejecutan en cada etapa.

### 4. Runners
Agentes que ejecutan los trabajos del pipeline.

## Estructura del .gitlab-ci.yml

```yaml
stages:
  - build
  - test
  - quality
  - package
  - deploy

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=.m2/repository"

cache:
  paths:
    - .m2/repository/
    - target/

build:
  stage: build
  image: maven:3.8.1-openjdk-11
  script:
    - mvn clean compile
```

## Configuración del Proyecto

### 1. Variables de Entorno en GitLab
Configura estas variables en GitLab → Settings → CI/CD → Variables:

```
SONAR_HOST_URL: http://sonarqube:9000
SONAR_TOKEN: <tu-token-sonarqube>
CI_REGISTRY: registry.gitlab.com
CI_REGISTRY_USER: <tu-usuario>
CI_REGISTRY_PASSWORD: <tu-password>
DATABASE_PASSWORD: <password-seguro>
SSH_PRIVATE_KEY: <clave-ssh-para-deploy>
PRODUCTION_SERVER: <ip-servidor-produccion>
```

### 2. Configurar Runner
Para proyectos privados, necesitas configurar un GitLab Runner:

```bash
# Instalar GitLab Runner
sudo gitlab-runner install

# Registrar runner
sudo gitlab-runner register
```

## Pipeline Explicado

### Stage: Build
```yaml
build:
  stage: build
  image: maven:3.8.1-openjdk-11
  script:
    - mvn clean compile
  artifacts:
    paths:
      - target/
    expire_in: 1 hour
```

**Propósito**: Compila el código fuente y genera artefactos.

### Stage: Test
```yaml
test:
  stage: test
  image: maven:3.8.1-openjdk-11
  script:
    - mvn test
  artifacts:
    reports:
      junit:
        - target/surefire-reports/TEST-*.xml
    paths:
      - target/site/jacoco/
  coverage: '/Total.*?([0-9]{1,3})%/'
```

**Propósito**: Ejecuta pruebas unitarias y genera reportes de cobertura.

### Stage: Quality (SonarQube)
```yaml
sonarqube-check:
  stage: quality
  image: maven:3.8.1-openjdk-11
  script:
    - mvn verify sonar:sonar
      -Dsonar.projectKey=$CI_PROJECT_NAME
      -Dsonar.host.url=$SONAR_HOST_URL
      -Dsonar.login=$SONAR_TOKEN
  allow_failure: true
```

**Propósito**: Análisis estático de código para detectar bugs, vulnerabilidades y code smells.

### Stage: Package (Docker)
```yaml
docker-build:
  stage: package
  image: docker:20.10.16
  services:
    - docker:20.10.16-dind
  script:
    - docker build -t $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA .
    - docker push $CI_REGISTRY_IMAGE:$CI_COMMIT_SHA
```

**Propósito**: Construye y publica la imagen Docker.

### Stage: Deploy
```yaml
deploy-production:
  stage: deploy
  script:
    - ssh user@$PRODUCTION_SERVER "docker stack deploy -c docker-compose.swarm.yml tech-stack"
  environment:
    name: production
    url: http://produccion.ejemplo.com
  only:
    - master
  when: manual
```

**Propósito**: Despliega a diferentes entornos.

## Ejercicios Prácticos

### Ejercicio 1: Pipeline Básico
1. Haz un commit con cambios en el código
2. Ve a GitLab → CI/CD → Pipelines
3. Observa la ejecución del pipeline
4. Revisa los logs de cada job

### Ejercicio 2: Configurar SonarQube
1. Levanta SonarQube con docker-compose
2. Crea un proyecto en SonarQube
3. Genera un token
4. Configura las variables en GitLab
5. Ejecuta el pipeline y revisa el Quality Gate

### Ejercicio 3: Deploy Manual
1. Configura las variables de deploy
2. Ejecuta el job de deploy manual
3. Verifica que la aplicación esté desplegada

## Mejores Prácticas

### 1. Seguridad
- Nunca hardcodear credenciales
- Usar variables protegidas
- Limitar acceso a runners
- Escanear imágenes Docker

### 2. Performance
- Usar cache eficientemente
- Paralelizar jobs cuando sea posible
- Optimizar imágenes Docker
- Usar artifacts selectivamente

### 3. Mantenibilidad
- Usar templates para jobs similares
- Documentar variables complejas
- Usar semantic versioning
- Configurar branch policies

## Troubleshooting Común

### Pipeline Falla en Build
```yaml
# Verificar logs específicos
build:
  script:
    - mvn clean compile -X # Modo debug
  after_script:
    - find target -name "*.log" -exec cat {} \;
```

### Problemas con Cache
```yaml
cache:
  key: "$CI_COMMIT_REF_SLUG"
  paths:
    - .m2/repository/
  policy: pull-push
```

### Jobs que se Cuelgan
```yaml
job_with_timeout:
  timeout: 30m
  script:
    - long-running-command
```

## Monitoreo y Métricas

