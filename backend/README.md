# Rent a Car API

API para gerenciamento de aluguéis de veículos desenvolvida com Spring Boot.

## Estrutura do Projeto

O projeto segue os princípios da Clean Architecture com as seguintes camadas:

### Application Layer
- `application/dtos`: Objetos de transferência de dados (DTOs)
- `application/usecases`: Implementações dos casos de uso

### Domain Layer  
- `domain/entities`: Entidades do domínio (Car, Customer, Rental)
- `domain/repositories`: Interfaces dos repositórios
- `domain/services`: Serviços de domínio
- `domain/strategy`: Implementações de estratégias (ex: cálculo de preço)
- `domain/vo`: Value Objects

### Infrastructure Layer
- `infrastructure/config`: Configurações da aplicação
- `infrastructure/repositories`: Implementações dos repositórios
- `infrastructure/web`: Controladores REST

## Princípios SOLID Aplicados

1. **Single Responsibility Principle (SRP)**
   - Cada caso de uso tem uma única responsabilidade
   - Repositórios são responsáveis apenas por acesso a dados

2. **Open/Closed Principle (OCP)**
   - Estratégias de cálculo de preço permitem extensão sem modificar código existente
   - Novos tipos de cálculo podem ser adicionados implementando PriceCalculationStrategy

3. **Liskov Substitution Principle (LSP)**  
   - Implementações de repositórios podem ser substituídas sem afetar o comportamento
   - Estratégias de cálculo são intercambiáveis

4. **Interface Segregation Principle (ISP)**
   - Interfaces específicas para cada repositório (CarRepository, CustomerRepository)
   - Interfaces pequenas e focadas

5. **Dependency Inversion Principle (DIP)**
   - Casos de uso dependem de abstrações (interfaces) de repositórios
   - Inversão de dependência através de injeção

## Clean Architecture

O projeto segue os princípios da Clean Architecture com:

- **Independência de frameworks**: O core do domínio não depende do Spring
- **Testabilidade**: Casos de uso e regras de negócio podem ser testados isoladamente  
- **Independência de UI**: A API REST é uma mera interface, podendo ser substituída
- **Independência de banco de dados**: Repositórios são abstrações, implementações podem variar

## Como Executar

1. Instale as dependências:
```bash
mvn install
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

3. Acesse a documentação da API:
```
http://localhost:8080/swagger-ui.html
```

## Exemplos de Requisições

Verifique o arquivo `requests.http` para exemplos de chamadas à API.
