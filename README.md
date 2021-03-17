# MalvelHeroes
Esse repositório se dedica a testar alguns conceitos de ferramentas, arquitetura simples e de fácil escalabilidade, testes, etc.

Para isso faço uso da API pública da Marvel, que pode se acessada e utilizada pelo endereço https://developer.marvel.com/docs.

## Uso do app

O app pode ser utilizado sem restrições! O código pode ser baixado e modificado livremente.

Porém, para reproduzí-lo é necessário que seja criada uma conta de desenvolvedor para consumir a API da Marvel.

Uma vez com a conta criada, você terá acesso a uma chave pública e uma chave privada. Substitua as chaves públicas e provadas encontradas no arquivo build.gradle do app (são os campos PUBLIC_KEY_MARVEL_API e PRIVATE_KEY_MARVEL_API, que aparecem nas build variants de Debug e Release).

OBS: Nunca faça commit ou compartilhe sua chave privada!

## Detalhe de funcionamento do app

O app possui 3 telas. A primeira permite a pesquisa de personagens Marvel por meio de corresponência de texto. A segunda exibe os personagens marcados como favorito e a terceira tela mostra os detalhes de um personagem selecionado.

Todas as telas possuem opção para marcar/desmarcar um personagem como favorito!

[Demonstração do uso do app](https://github.com/filipibrentegani/MalvelHeroes/edit/main/heroes.gif)
