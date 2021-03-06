**Avaliação JPA**
===============

Questionário avaliativo da disciplina de JPA  para o curso de **Desenvolvimento Full Stack**.  <i class="icon-desktop"></i>

--------------------------------

##**Qual a responsabilidade/objeto das anotações:**

> **@MappedSuperclass**
> Esta anotação é referenciada a uma classe, que servirá de _base_ para uma ou mais entidades "**@Entity**". Esta classe se aproximará bastante a uma classe abstrata, tendo somente comportamentos e estados, por isso é uma boa prática declara-la como tal.

-----------------------------
>**@Version**
> Esta anotação é utilizada para controle de versão de um objeto. Utiliza-se este tipo de anotação para quando se considera um Controle de Concorrência Otimista (**OCC**), aquele no qual acredita-se que a probabilidade de duas transações utilizarem o mesmo objeto é minima, uma vez que a própria transação não verifica isto, o hibernate se utilizará deste atributo _**@Version**_ para identificar a versão da entidade.

-----------------------------
>**@Entity**
> Anotação responsável por mapear uma tabela no seu banco de dados. Quando uma classe é anotado assim, se torna obrigatório declarar qual será o atributo que representará o Id, este atributo deverá ser anotado com _**@Id**_. Os atributos desta classe se tornaram colunas em sua tabela, retirando os atributos staticos ou que estejam com a anotação _**@Transient**_.

-----------------------------
>@Table
> Esta anotação é também utilizada em uma classe que será "persistível", ela está anotada juntamente com o _**@Entity**_. Servirá para alterar valores referentes a tabela e tem como atributos modificadores os seguintes:
> **name:** responsável por alterar o nome da tabela no banco. O padrão é o próprio nome da classe.
> **schema:** responsável por definar qual será o schema para a tabela. O padrão será o schema principal o do usuário.
> **catalog:** responsável por restringir e especificar juntamente com o schema a visibilidade da tabela para o usuário. 
> **uniqueConstraints:** responsável por criar restrições para a tabela. Esta funcionalidade sobrecreve quaisquer outras retrições e somente será possível na opção de criação de banco.

-----------------------------
>@Id
>: Esta anotação é necessária para informar qual é o seu atributo responsável por representar o Id da sua tabela. Para cada entidade anotada com _**@Entity**_ será necesário informa pelo menos **1** atributo com _**@Id**_. Uma vez anotado mais de um atributo, a classe trabalhará considerando que se tem uma **"chave primária composta"**.

-----------------------------
>@GeneratedValue
>: Esta anotação é responsável por definir como será feita a geração do Id para entidade, anotada juntamente com o _**@Id**_, ela aceita dois parametros tais como:

>: **strategy:** este paremetro define qual será a estratégia para gerar a chave primária para entidade anotada. Pode-se informar os parametros de acordo com a classe _**GenerationType**_ que tem como valores: **AUTO, IDENTITY, SEQUENCE e TABLE**.

>: **generator:** este parametro é utilizado para informar qual será a tabela ou a sequence para a geração dos valores. Necessário quando for informada _**strategy: SEQUENCE ou TABLE**_.

-----------------------------
>@Column
>: Esta anotação é utilizada para personalizar as colunas de sua tabela, podendo ser anotada em cada atributo de sua entidade. Ela possui as seguintes opções:

> **name:** parametro responsável por alterar o nome da coluna em sua tabela. Por padrão ele criará o mesmo nome de seu atributo.

> **columnDefinition:**  este parametro será um fragmendo do SLQ usado ao gerar a coluna. Ao utilizar este parametro **NÃO** porderá ser utilizado os seguintes parametros: **lenght, precision, scale, nullable e unique**.

>: **length:**  quando o atributo são do tipo _"string/varchar"_ este parametro ficará responsável por definir qual será o tamanho máximo para o atributo.

>: **precision:** quando o atributo mapeado for um _float_ ou _double_ este paremetro fica responsável por definir qual será o tamanho máximo para o numero.

>: **scale:**  quando o atributo mapeado for um _float_ ou _double_ este paremetro fica responsável por definir qual será a precisão de casas decimais para o atributo.

>: **nullable:**  parametro responsável por indicar se o atributo poderá ou não ser nulo ao inserir um novo registro.

>: **unique:**  parametro responsável por garantir que o valor do atributo seja unico entre os registros da tabela.

>: **insertable:**  os valores para este parametro poderá ser: **true/false**. 
Para **true**: se o objeto do atributo for **novo**, ou seja, ainda não esteja cadastrado, a entidade que está sendo salva poderá também salvar este novo objeto. Para **false: isto não poderá ocorre.

>: **updatable:**  este parâmetro funciona similar ao **insertable**, porém dará o poder de atualizar o objeto casa o mesmo esteja diferente.

>: **table:**  parâmetro responsável por identificar em qual tabela se encontra a coluna. O padrão é a entidade principal.

-----------------------------
>@Basic
>: Esta anotação está entre as mais básicas em uma coluna. Para esta anotação se tem duas possibilidades de passagem de parâmetros que são : 

>: **fetch:** este parâmetro dirá se a propriedade deverá ou não carregar ao se realizar uma consulta pela entidade. Os valores possíveis para este estão na classe _**FetchType**_ que poderão ser **EAGER ou LAZY**.
>Para utilizar **EAGER**, é quando se quer que o _objeto/atributo_ será carregado ao trazer sua entidade, e para **LAZY** ele não deverá ser carregado. O padrão caso não especificado é **EAGER**.

-----------------------------
>@Temporal
>: Esta anotação é utilizada para atributos do tipo **Date**. Neste caso voce poderá informar três tipos que seu atributo poderá ser de três tipos, dia acordo com a classe _**TemporalType**_ que são:
> **TIME:** para quando o atributo for somente de hora, ex: _23:59:59_.
> **DATE** para quando o atributo for somente data, ex: _31/12/2000_.
> **TIMESTAMP** para quando o atributo conter data e hora, ex: _31/12/2000 23:59:59_. 

-----------------------------
>@ManyToOne
>:  Esta anotação é utilizada para indicar que se trata de um relacionamento de muitos para um. Os parâmetros que são aceitos para esta anotação são os seguintes: _**joinColumn, fetch e cascade**_.  
>: **joincolumn:** este parâmetro é usado para especificar a coluna que contem a chave estrangeira de cada relacionamento.
>: **Fetch e cascade:** já foram explicados anteriormente e seu funcionamento é o mesmo.


-----------------------------
>@ManyToMany
>: Este relacionamento informa que muitos registros de uma entidade estão relacionados com muitos registros de outra entidade. Os parâmetros que são aceitos para esta anotação são os seguintes: _**mappedBy, targetEntity, fetch e cascade**_.  
>: **mappedBy:** este parâmetro informa o atributo que é dono do relacionamento.
>: **targetEntity:** este parâmetro informa que a classe entity que é alvo da associação..
>: **Fetch e cascade:** já foram explicados anteriormente e seu funcionamento é o mesmo.

-----------------------------
>@OneToOne
>: Este relacionamento informa que um registro de uma entidade está relacionado com apenas uma outra entidade. Os parâmetros que são aceitos para esta anotação são os seguintes: _**orphanRemoval, optional, fetch e cascade**_. 
>: **orphanRemoval:** este parâmetro define que uma entidade dependente, caso não tenha relacionamento, será removida do banco de dados.
>: **optional:** este parâmetro informa você não será obrigado a ter um atributo ao persistir sua entidade.
>: **Fetch e cascade:** já foram explicados anteriormente e seu funcionamento é o mesmo.
-----------------------------
>@JoinColumn
>:  Esta anotação é utilizada para indicar uma coluna que servirá de chave primária na tabela de relacionamento. Os parâmetros que são aceitos para esta anotação são os seguintes: _**name, referencedColumnName**_. 
>: **name:** este parâmetro informa o nome que a coluna da tabela terá, 
>: **referencedColumnName:** este parâmetro informa a chave primária da tabela dona do relacionamento; 


---------------------------------------
###**Como instânciar Criteria do Hibernate através do EntityManager?**
Deve-se criar uma entidade com o méoto createCriteria, passando como parametros qual é a classe da entidade da consulta e também o valor do alies da entidade na query.

>: **Cogigo:** Criteria criteria = createCriteria(Produto.class, "p");

---------------------------------------
###**Como abrir uma transação?**
Deve-se ter um objeto do EntityManager instanciado através de uma factory: EntityManagerFactory, com a entidade chama-se o método _begin()_.

>: **Cogigo: 1-** EntityManager em;
>: **Cogigo: 2-** EntityManagerFactory factory
>: **Cogigo: 3-** factory = Persistence.createEntityManagerFactory("XXXX");
>: **Cogigo: 4-** em = factory.getEntityManager();
>: **Cogigo: 5-** em.getTransaction().begin();

---------------------------------------
###** Como fechar uma transação?**
Chama-se o método _close()_ do seu EntityManager instanciado;

>: **Cogigo: 6-** em.close();

---------------------------------------
###** Como criar e executar uma query com JPQL?**
Cria-se através de uma StringBuilder a sua JPQL e a executa através de uma Query.

>: **Cogigo: 1-** StringBuilder jpql = new StringBuilder();
>: **Cogigo: 2-** jpql.append("SELECT COUNT(v.id) ");
>: **Cogigo: 3-** jpql.append(" FROM Venda v ");
>: **Cogigo: 4-** Query query = getEm().createQuery(jpql.toString());
>: **Cogigo: 5-** Long qtdProdutosVenda = (Long) query.getSingleResult();

---------------------------------------
###** Qual a responsabilidade dos valores FetchType.LAZY e FetchType.EAGER?**

>: **FetchType.LAZY:** faz com que o atributo que esteja com este marcação não seja carregado do banco até que você precise dele, ou seja, será carregado quando se solicitar esta informação.

>: **FetchType.EAGER:** este é o oposto do ao LAZY, o EAGER traz os dados mesmo que você não vá utilizá-los.

---------------------------------------
###** Qual a responsabilidade dos valores CascadeType.PERSIST e CascadeType.REMOVE?**

>: **CascadeType.PERSIST:** 

>: **CascadeType.REMOVE:** 

---------------------------------------
###** Como fazer uma operação BATCH (DELETE ou UPDATE) através do EntityManager?**


---------------------------------------
###** Qual a explicação para a exception LazyInitializationException?**


 
