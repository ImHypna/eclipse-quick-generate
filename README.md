# Eclipse Quick Generate

Plugin para Eclipse que adiciona um menu de geração de código rápido, similar ao `Ctrl+N` do IntelliJ IDEA.

## Atalho

`Ctrl + Alt + Shift + N`

## O que faz

Abre uma janela de busca com as seguintes opções:

- Generate Getters and Setters
- Generate Constructor
- Generate toString()
- Generate hashCode() and equals()
- Generate Delegate Methods
- Override/Implement Methods
- Organize Imports
- Format Document

## Instalação

1. Baixe o `.jar` em [Releases](../../releases)
2. Copie para a pasta `dropins` do seu Eclipse:
   ```
   <eclipse-dir>/dropins/
   ```
3. Reinicie o Eclipse

## Requisitos

- Eclipse 2024-12 ou superior
- Java 21+

## Build

1. Importe o projeto no Eclipse como **Plug-in Project**
2. `File → Export → Plug-in Development → Deployable plug-ins and fragments`
3. Copie o `.jar` gerado para o `dropins`
