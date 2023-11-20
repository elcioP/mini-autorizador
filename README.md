# mini-autorizador
Esse projeto foi feito para autorizar processo de cartao de beneficios
a classe CartaoController cria cartões e obtem seu saldo através da classe CriarCartaoService
A classe TransacaoController está ligada ao processo de transação do cartão e validações para essa transação 
validações sendo se o cartão existe, obtém saldo e se a senha é valida

#Controllers
Ambas as controller tem um método de validação de dados de entradas (Request), esse método é necessário para que a anotação @Valid funcione
(a qual verifica se os campos da request foram  preenchidos)
