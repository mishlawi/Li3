import Models.Catalogos.*;
import Common.Constantes;
import Models.Faturacao.Faturacao;
import Models.Faturacao.IFaturacao;
import Models.Filial.Filial;
import Models.Filial.IFilial;
import Models.Venda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;


public class GereVendasModel {
    private ICatProdutos catprodutos;
    private ICatClientes catclientes;
    private IFaturacao faturacao;
    private List<IFilial> filiais;

    public GereVendasModel() {
        this.faturacao = new Faturacao();
        this.catprodutos = new CatProds();
        this.catclientes = new CatClientes();
        List<IFilial> fl = new ArrayList <> ();
        for (int i=0; i< Constantes.FILIAIS; i++){
            fl.add(new Filial ());
        }
        this.filiais=fl;
    }

    public GereVendasModel(IFaturacao faturacao, ICatProdutos catprodutos, ICatClientes catclientes,List<Filial>filiais) {
        this.catprodutos = catprodutos.clone();
        this.catclientes = catclientes.clone();
        this.faturacao = faturacao.clone();
        this.filiais = filiais.stream().map(Filial::clone).collect(Collectors.toList());
    }

    public GereVendasModel(GereVendasModel model){
        this.catclientes=model.getCatClientes();
        this.catprodutos=model.getCatProdutos();
        this.faturacao=model.getFaturacao();
        this.filiais=model.getFiliais();
    }

    /**
     * Getters
     */
    public ICatProdutos getCatProdutos() {
        return catprodutos.clone();
    }

    public ICatClientes getCatClientes() {
        return catclientes.clone();
    }

    public IFaturacao getFaturacao() {
        return faturacao.clone();
    }

    public List<IFilial> getFiliais() {
        return filiais.stream().map(IFilial::clone).collect(Collectors.toList());
    }

    /**
     * Setters
     */
    public void setCatprodutos(CatProds catprodutos) {
        this.catprodutos = catprodutos.clone();
    }

    public void setCatclientes(CatClientes catclientes) {
        this.catclientes = catclientes.clone();
    }

    public void setFaturacao(Faturacao faturacao) {
        this.faturacao = faturacao.clone();
    }

    public void setFiliais(List<Filial> filiais) {
        this.filiais = filiais.stream().map(Filial::clone).collect(Collectors.toList());
    }

    /**
     * Clone
     */
    public GereVendasModel clone(){
        return new GereVendasModel(this);
    }

    public void lerFilesProdutos (String filename){
        BufferedReader inFile = null;
        String line=null;
        try{
            this.catprodutos= new CatProds();
            Crono.start();
            inFile = new BufferedReader(new FileReader(filename));
            while ((line=inFile.readLine())!=null){
                this.catprodutos.insereProduto(new Produto(line));
            }
            System.out.println("\n[Leitura do ficheiro de produtos]: " + Crono.stop() + " s");
        }
        catch(IOException e) {System.out.println(e);}
    }

    public void lerFilesClientes (String nomeFich){
        BufferedReader inFile = null; String linha=null;
        try{
            this.catclientes = new CatClientes ();
            Crono.start();
            inFile = new BufferedReader (new FileReader (nomeFich));
            while ((linha=inFile.readLine())!=null){
                this.catclientes.insereCliente(new Cliente(linha));
            }
            System.out.println("\n[Leitura do ficheiro de clientes]: " + Crono.stop() + " s");
        }
        catch(IOException e) {System.out.println(e);}
    }

    public Venda linhaToVenda (String linha){
        String codProd, codCli;
        char tipoCompra;
        int mes = 0; int filial=0; int quant=0; float preco=0;
        String [] campos = linha.split(" ");
        codProd = campos [0];
        codCli = campos [4];
        tipoCompra = campos[3].charAt(0);
        try{
            preco = Float.parseFloat(campos[1]);
            quant = Integer.parseInt(campos[2]);
            mes = Integer.parseInt(campos[5]);
            filial = Integer.parseInt(campos[6]);
        }
        catch(NumberFormatException e) {return null;}

        Venda v = new Venda(new Produto(codProd),new Cliente(codCli),preco, quant,tipoCompra,mes,filial);

        if(v.validaV(this.catprodutos,this.catclientes)) { return v; }
        else return null;
    }

    public void lerVendas(String filename){
        BufferedReader inFile = null;
        String linha = null;

        int erradas,num_compras_preco_0 ;
        erradas = num_compras_preco_0 = 0;

        try{
            Crono.start();
            inFile= new BufferedReader(new FileReader(filename));
            int x=0;
            while((linha = inFile.readLine()) != null){
              Venda v = linhaToVenda(linha);
                if(v!=null){
                    x++;
                    this.faturacao.insereVenda(v);
                    IFilial f=this.filiais.get(v.getFilial()-1);
                    f.insereVenda(v);
                    if(v.getPreco() == 0) num_compras_preco_0++;
                }else{erradas++;}
            }
            System.out.println("\n[Leitura do ficheiro de vendas]: " + Crono.stop() + " s");
            System.out.println("\n[Linhas lidas]: " + x);
            System.out.println("\n[Linhas  erradas: " + erradas);
        }
        catch(IOException e){System.out.println(e);};
    }

    public String [] lerConfigs(){
        BufferedReader inFile=null;
        String linha=null;
        String [] configs = new String [4];
        int i=0;
        try{
            inFile = new BufferedReader (new FileReader (Constantes.CONFIGS_FILE_NAME));
            while((linha=inFile.readLine())!=null && i<4){
                configs[i++]=linha;
            }
        }
        catch(IOException e) {System.out.println(e);}

        return configs;
    }



    //Consultas estatisticas
    /**
     * 1.1
     */



    /**
     * 1.2
     */
/*
    public int numeroComprasMes(int mes){
        return (int) this.vendas.getVendas().values().stream().filter(e->e.getMes()==(mes)).count();
    }



    //Consultas interativas

    //1)
    public List<Produto> produtosNaoComprados(){
        return this.produtos.getProdutos().stream().filter(e-> !(vendas.existeProduto(e))).collect(Collectors.toList());
    }

    //2) FALTA FILIAL A FILIAL(o que isso quer dizer eu nao sei :P)
    public ParQuerie2 numeroTotalVendasEClientesMes(int mes){
        ParQuerie2 p = new ParQuerie2();
        List<Cliente> c = Collections.singletonList((Cliente) this.vendas.getVendas().values().stream().filter(e -> e.getMes() == (mes)).collect(Collectors.toList()));

        p.setNrVendas(numeroComprasMes(mes));
        p.setNrClientes((int) c.stream().distinct().count());
        return p;
    }

    //3)
    public ParQuerie3 quantasComprasFezPMes(String cod){
        float faturado = 0;
        List<Venda> vendas = this.vendas.getVendas().values().stream().filter(e->e.getCliente().equals(cod)).collect(Collectors.toList());
        ParQuerie3 p = new ParQuerie3();
        for (int i=0;i<12;i++){
            int aux = i;
            p.setVenda(aux,(int)vendas.stream().filter(e->e.getMes()==(aux+1)).count());
            p.setProdutos(aux,(int)vendas.stream().filter(e->e.getMes()==(aux+1)).distinct().count());
            faturado += vendas.stream().filter(e->e.getMes()==(aux+1)).mapToDouble(c->c.getPreco()).sum();
        }
        p.setTotalFaturado(faturado);
        return p;
    }
    */

}