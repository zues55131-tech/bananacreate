# Banana Create (addon do Create, NeoForge 1.21.1)

## Gerar o .jar

Requisitos: Java 21 (JDK) e internet (o Gradle baixa NeoForge, Create, Ponder e Flywheel).

```
./gradlew build        # Windows: gradlew.bat build
```

O mod fica em `build/libs/bananacreate-0.1.0.jar`.

Para jogar: coloque esse `.jar` na pasta `mods` de uma instalacao NeoForge 1.21.1,
junto com **Create 6.0.6+**, **Ponder** e **Flywheel** (o Create ja pede os dois).

Para testar direto do codigo, sem gerar o jar: `./gradlew runClient`.

## O que tem

- Bananeira de verdade: tronco, tabuas, folhas que dao banana (clique direito), muda e geracao em selvas.
- Canhao de Banana e ferramentas de banana.
- Fluido de Sangue + Balde de Sangue.
- Extrator de Sangue: maquina cinetica do Create. Gira -> fere mobs em cima e enche tanque.
- Centrifuga de Sangue: maquina cinetica. Recebe sangue (canos do Create) -> Frascos de Sangue.
- Seringa de Tita: clique direito em mob hostil -> vira tita (escala x6).
- Esteira de Tita: GERADOR cinetico. Titas por perto andam em circulo e geram 16 RPM cada (max 64).
  Monte uma arena cercada de uns 13x13 com a esteira no centro.
- Cyberware: Placa Cromada (prensa do Create), Chip Neural, Laminas de Braco Cromadas, Injetor de Overclock.
- Martelo do Copyright: 3 golpes no mesmo alvo = removido por violacao de direitos autorais.
- Receitas usam itens do Create (shaft, casings, electron tube, precision mechanism, sheets...).

## Pontos para conferir no primeiro build

O codigo foi escrito sem poder compilar. Se der erro, mande a mensagem completa. Os pontos mais provaveis:

1. `KineticBlockEntity.write/read` (assinatura com `HolderLookup.Provider`).
2. `lastCapacityProvided` em `TitanTreadmillBE` (campo protegido do Create).
3. Receitas `create:pressing` e `create:cutting` em `data/bananacreate/recipe/` (formato do Create 6).
4. Nome de itens do Create usados nas receitas (ex.: `create:brass_sheet`, `create:large_cogwheel`).
