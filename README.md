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

## Novidades desta versao

**Casca de banana 3D.** O Canhao de Banana agora deixa uma casca (bloco 3D com petalas) no chao onde acerta. Quem pisa
(andando/correndo) escorrega e e lancado para frente; agachar evita. Cada casca aguenta 3 escorregoes.
A casca tambem pode ser colocada a mao (item = bloco).

**Cyberware no corpo (visivel no seu personagem e nos outros jogadores).** Clique direito no implante para instalar
(custa 3 coracoes), Bisturi Cromado remove e devolve o item.
- Bracos de Gorila: +dano, +empurrao, +alcance, mineracao rapida, um pouco mais lento. Tecla **N** = pancada no chao.
- Laminas de Louva-a-Deus: tecla **G** estende/recolhe; estendidas dao +dano, +velocidade de ataque e alcance.
- Lancador de Projeteis (ombro): tecla **V** dispara mini-foguete (1 polvora). Explode sem quebrar blocos.
- Monofio (pulso): tecla **B** chicoteia em linha reta (9 blocos), fere, aplica definhar e puxa os alvos.
As teclas podem ser mudadas em Controles. Os implantes sao feitos em **Montagem Sequencial do Create**
(receitas em `data/bananacreate/recipe/sequenced/`).

**Ordem Paranormal + Create.**
- Sangue: Extrator e Centrifuga (ja existiam).
- Morte: **Sifao da Morte** (cinetico) puxa e drena monstros; quem morre vira Essencia de Morte.
- Energia: **Dinamo do Caos** (gerador cinetico) com RPM e sentido aleatorios; nos picos solta descargas.
- Medo: **Selo do Medo** (cinetico) afasta e enfraquece monstros num raio que cresce com o RPM.
- Conhecimento/essencias: **Haunting** (ventilador do Create sobre fogo da alma) transforma osso, ametista, olho de aranha
  e livro em Essencia de Morte, Fragmento de Caos, Essencia de Medo e Essencia de Conhecimento.

**Visual.** Texturas e modelos novos no estilo Create (casings com moldura e rebites, eixo atravessando as maquinas,
tanque de vidro de sangue). Os scripts `tools/gen_assets.py` e `tools/gen_models.py` regeneram tudo (Python + Pillow).

## Pontos para conferir no primeiro build

O codigo foi escrito sem poder compilar. Se der erro, mande a mensagem completa. Os pontos mais provaveis:

1. `KineticBlockEntity.write/read` (assinatura com `HolderLookup.Provider`).
2. `lastCapacityProvided` em `TitanTreadmillBE` (campo protegido do Create).
3. Receitas `create:pressing` e `create:cutting` em `data/bananacreate/recipe/` (formato do Create 6).
4. Nome de itens do Create usados nas receitas (ex.: `create:brass_sheet`, `create:large_cogwheel`).
5. Receitas `create:haunting` e `create:sequenced_assembly` (formato do Create 6 / 1.21.1).
6. Camada do jogador: `EntityRenderersEvent.AddLayers` + `ModelPart.render(...)` e anexar pecas ao braco/tronco.
7. Pacotes de rede (`RegisterPayloadHandlersEvent`) e o anexo `ModAttachments.CYBERWARE`.

Limitacoes conhecidas: as maquinas nao tem animacao de rotacao (modelo estatico; animar exige Flywheel Visual) e o cyberware
nao aparece na mao em primeira pessoa.
