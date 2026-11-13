# EasyShop — Persistência Poliglota Android

Protótipo Android de mini e-commerce que demonstra dois mecanismos de persistência local na mesma aplicação Kotlin. A interface possui duas abas: **Produtos (NoSQL)** e **Clientes (SQL)**.

## Integrantes

- Bruna Maria Marques
- Giovanne Maia Bartelega

## Objetivo atendido

| Domínio | Persistência | Motivo |
| --- | --- | --- |
| Catálogo de produtos | ObjectBox, banco NoSQL local orientado a objetos/documentos | Um produto pode ter qualquer conjunto de atributos sem alterar tabelas ou criar colunas nulas. |
| Cadastro de clientes | SQLite, por meio do Room (`easyshop-customers.db`) | Nome e e-mail têm formato conhecido; o e-mail é único e é validado antes da inserção. |

O ObjectBox é um banco NoSQL embarcado no dispositivo. Cada produto é salvo como um documento/objeto local e seus atributos variáveis ficam serializados em JSON dentro do documento. Assim, uma camisa pode salvar `Tamanho` e `Cor`, enquanto um notebook salva `RAM`, `Processador` e `Voltagem`, sem migração de schema.

## Arquitetura em camadas

```text
presentation/   MainActivity, Fragments, ViewModels e Adapters
       ↓
domain/         Services e contratos de repositório (validação/orquestração)
       ↓
data/sql/       Room, entidade Customer, DAO e repositório SQL
data/nosql/     Entidade ObjectBox, documento Product e repositório NoSQL
```

A camada de apresentação não acessa arquivos, Room ou DAOs. Ela chama os `Services` através dos `ViewModels`. A composição das dependências é feita em `EasyShopApplication`.

## Funcionalidades

- Cadastro e listagem reativa de clientes em SQLite/Room.
- Validação de nome completo, formato de e-mail e unicidade de e-mail.
- Cadastro e listagem de produtos como documentos NoSQL locais.
- Preço obrigatório e maior que zero.
- Categoria opcional e atributos dinâmicos no formato `Chave: Valor`, um por linha.
- Persistência entre reinicializações do app, em arquivos privados do Android.

## Tecnologias

- Kotlin
- Android SDK 35, View Binding, Fragments, ViewPager2 e Material Components
- Room 2.6.1 (SQLite local)
- ObjectBox 5.4.2 (banco NoSQL local)
- Gson 2.11.0 (serialização dos atributos dinâmicos no documento)
- Coroutines, LiveData e ViewModel

## Como executar

1. Instale o Android Studio (JDK 17) e o Android SDK 35.
2. Abra esta pasta no Android Studio e aguarde a sincronização do Gradle.
3. Crie ou selecione um emulador Android com API 26 ou superior.
4. Clique em **Run** para instalar e executar o app.

> Na primeira execução, o Gradle baixará as dependências. O projeto requer conexão apenas nessa etapa de sincronização.

## Roteiro de demonstração em vídeo

1. Abra **Produtos (NoSQL)** e cadastre uma camiseta com `Tamanho: M` e `Cor: Azul`.
2. Cadastre um notebook usando os atributos `Processador: i7`, `RAM: 16 GB` e `Voltagem: 220V`.
3. Mostre na lista que os dois documentos possuem atributos diferentes sem qualquer alteração de schema.
4. Abra **Clientes (SQL)** e cadastre um cliente com nome completo e e-mail válido.
5. Tente cadastrar o mesmo e-mail novamente para evidenciar a restrição de unicidade da tabela relacional.
6. Feche e abra o aplicativo para demonstrar que os dois conjuntos de dados continuam locais e persistidos.
