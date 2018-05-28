# AutoCompleteTextPopUp
Classe Java que cria uma janela popup com uma lista de opções predefinidas para autocomplete de campos JTextField

## Como usar:
- Inclua a classe no seu sistema
- Crie um array de strings com as opções que ficarão disponíveis para o autocomplete. Por exemplo:
```
  ArrayList<String> words = new ArrayList<>();
  words.add("joão da silva");
  words.add("joaquim manoel");
  words.add("manoel de nóbrega");
  words.add("pedro alcântara");
  words.add("rosa maria");
  words.add("maria de jesus");
  words.add("jose pedro");
 
```
- Crie uma nova instância da classe AutoCompleteTextPopUp passando como parâmetros o seu JTextField, o jDialog onde está o campo de texto e a lista de opções:

```
AutoCompleteTextPopUp autoComplete = new AutoCompleteTextPopUp(meuJTextField, meuJDialog, words);
```
- Pronto! 

![alt text](http://leandrobetin.com.br/assets/imagens/github/exemplo01Java.png)
