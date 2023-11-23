import greenfoot.*;
import java.util.List;
import java.util.Random;

public class CodeCrash extends World {
    /*
     * Atributos do mundo
     */
    private int ladoDireito = getWidth();
    private int altura = getHeight()-100;
    
    private boolean iniciarEtapa = false;
    
    /*
     * Declarando os Jogadores 
     */ 
    Jogador jogador1 = new Jogador1();
    Jogador jogador2 = new Jogador2();
    
    /*
     * Configurações da Horda
     */
    
    private int tempoEsperaInvocarInimigo = 0;
    private int totalInimigoInvocEmHorda = 0;
    
    boolean chefeInvocado = false;
    int tempoComeçarFase = 120;
    boolean dialogo = false;
    Inimigo[] tiposDeInimigo;
    
    
    /*
     * Configurações dos Inimigos
     */
    private int inimigosMortos = 0;
    private boolean invocarInimigos = true;
    
    /*
     * Configurações dos Chefes
     */   
    private int totalChefesInvocados = 0;
    private int chefeMorto = 0;
    private int tempoEspera = 2*60;
    //boolean chefeInvocado = false;
    
    /*
     * Configuração das fases 
     */
    private int faseAtual = 4;
    
    GreenfootImage fundoFase1 = new GreenfootImage("Back01.png");
    GreenfootImage fundoFase2 = new GreenfootImage("Back03.png");
    GreenfootImage fundoFase3 = new GreenfootImage("Back02.png");
    GreenfootImage fundoFase4 = new GreenfootImage("Back04.png");
    
    /*
     * Configuração do Som
     */
    private GreenfootSound musicaDeFundo = new GreenfootSound("trilhaSonora.mp3");
    
    /*
     * Configurar Gifs do mundo
     */
    
    private String locDialogo1 = "Gifs/dialogo2_inicio-fase1.gif";
    
    
    public CodeCrash() {
        super(1220, 600, 1);
        fase();
        musicaDeFundo.setVolume(50);
        //Greenfoot.start();
        //prepare();
    }
    
    public void act() {
        
        if (!musicaDeFundo.isPlaying()) {
            musicaDeFundo.play();
        }
        
        if (!jogador1.estaVivo && !jogador2.estaVivo) {
            
            addObject(new ImagemFundo("menu.png"), getWidth() / 2, getHeight() / 2);

            if (Greenfoot.isKeyDown("r")) {
                retroceder();
            }
        }
        
        fase();
    }
    
    public void configurarJogadores() {
        if (jogador1.estaVivo()) {
            this.addObject(jogador1, 65, 535);
        }
        if (jogador2.estaVivo()) {
            this.addObject(jogador2, 170, 535);
        }
    }
    
    public void passarFase() {
        faseAtual++;
        
        jogador1.redefinirVida();
        jogador2.redefinirVida();
        redefinirConfiguracoes();
    }
    
    public void retroceder() {
        faseAtual = 1;
        removerTodosOsAtores();
        
        configurarJogadores();
        
        jogador1.redefinirVida();
        jogador1.reiniciarMunicao();
        
        jogador2.redefinirVida();
        jogador2.reiniciarMunicao();
        
        redefinirConfiguracoes();
        Greenfoot.delay(10);
    }
    
    public void removerTodosOsAtores() {
        List<Actor> atores = getObjects(Actor.class);
    
        for (Actor ator : atores) {
            removeObject(ator);
        }
    }
    
    public void redefinirConfiguracoes() {
        tempoEspera = 2*60;
        inimigosMortos = 0;
        totalChefesInvocados = 0;
        chefeMorto = 0;
        iniciarEtapa = false;
        
        tempoComeçarFase = 120;
        totalInimigoInvocEmHorda = 0;
        tempoEsperaInvocarInimigo = 0;
        chefeInvocado = false;
    }
    
    public void fase() {
        switch (faseAtual) {
            case 0: 
                Greenfoot.setWorld(new Menu());
                break;
            case 1:
                prepararFase1();
                break;
            case 2:
                prepararFase2();
                break;
            case 3:
                prepararFase3();
                break;
            case 4:
                prepararFase4();
                break;
            default:
                teste();
                break;
        }
    }
    
    /*
    public void prepararFase1() {
        setBackground(fundoFase1);
        configurarJogadores();
        
        inimigos = getObjects(Inimigo1.class);
        int numDeInimigos = inimigos.size();
        
        if (numDeInimigos < 2 && totalInimigosInvocados < 20) {
            Inimigo inimigo = new Inimigo1();
            int posY = getHeight() / 2;
            int posX;
            if (estaEsquerda) {
                posX = 0;
                estaEsquerda = false;
            } else {
                posX = getWidth();
                estaEsquerda = true;
            }
            addObject(inimigo, posX, posY);
            totalInimigosInvocados++;
        }
        
        if (numDeInimigos == 0 && totalInimigosInvocados == 20) {
            if (getObjects(RabulGenius.class).isEmpty() && !chefeInvocado) {
                
                Chefe chefe = new RabulGenius();
                addObject(chefe, getWidth(), 500);
                chefeInvocado = true;
                
            } else {
                List<RabulGenius> chefe = getObjects(RabulGenius.class);
                List<DroneMaluco> drone = getObjects(DroneMaluco.class);
                
                if (chefeInvocado && chefe.isEmpty() && drone.isEmpty()) {
                    passarFase();
                    numDeInimigos = 0;
                    totalInimigosInvocados = 0;
                    chefeInvocado = false;
                    fase();
                }
            }
        }
    }
    
    public void prepararFase2() {
        setBackground(fundoFase2);
        configurarJogadores();
    
        int posY = getHeight() / 2;
        int posX;
        if (!estaEsquerda) {
            posX = 0;
            estaEsquerda = true;
        } else {
            posX = getWidth();
        }
    
        int escolhaInimigo;
        Inimigo inimigo = null;
    
        if (totalInimigosInvocados < 40) {
            if (tempoDeEspera % (60 * 3) == 0) {
    
                for (int i = 0; i < 4; i++) {
                    escolhaInimigo = Greenfoot.getRandomNumber(3);
                    switch (escolhaInimigo) {
                        case 0:
                            inimigo = new Inimigo1();
                            break;
                        case 1:
                            inimigo = new DroneMaluco();
                            break;
                        case 2:
                            inimigo = new EspectroDoDesespero();
                            break;
                        default:
                            // Lógica de erro, caso necessário
                            break;
                    }
                    addObject(inimigo, posX, posY);
                    totalInimigosInvocados++;
                }
            }
            tempoDeEspera++;
        }
    
        List<Inimigo> inimigos = getObjects(Inimigo.class);
    
        if (totalInimigosInvocados == 40 && inimigos.isEmpty() && !chefeInvocado) {
            chefeInvocado = true;
        }
    
        if (chefeInvocado && getObjects(Chefe2.class).isEmpty()) {
            Chefe chefe = new Chefe2();
            addObject(chefe, getWidth(), 530);
        }
    
        List<Chefe2> chefe = getObjects(Chefe2.class);
        List<EspectroDoDesespero> espectro = getObjects(EspectroDoDesespero.class);
    
        if (chefeInvocado && chefe.isEmpty() && espectro.isEmpty()) {
            passarFase();
            totalInimigosInvocados = 0;
            chefeInvocado = false;
            fase();
        }
    } */
    
    public void prepararFase1() {
        try {
            final int totalInimigoAInvocar = 15;
            
            configurarJogadores();
            setBackground(fundoFase1);
            
            if (tempoComeçarFase > 0) tempoComeçarFase--;
            
            if (tempoComeçarFase == 0) {
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }
                
                tiposDeInimigo = new Inimigo[] {
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 3, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe1(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    redefinirConfiguracoes();
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase2() {
        try {
            final int totalInimigoAInvocar = 30;
            
            configurarJogadores();
            setBackground(fundoFase3);
            
            if (tempoComeçarFase > 0) tempoComeçarFase--;
            
            if (tempoComeçarFase == 0) {
                /*
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }*/
                
                tiposDeInimigo = new Inimigo[] {
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 6, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe2(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    redefinirConfiguracoes();
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase3() {
        
        try {
            final int totalInimigoAInvocar = 45;
            
            configurarJogadores();
            setBackground(fundoFase2);
            
            if (tempoComeçarFase > 0) tempoComeçarFase--;
            
            if (tempoComeçarFase == 0) {
                /*
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }*/
                
                tiposDeInimigo = new Inimigo[] {
                    new EspectroDoDesespero(),
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 9, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe3(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    redefinirConfiguracoes();
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void prepararFase4() {
        /*
        setBackground(fundoFase4);
        configurarJogadores();
        
        int numeroDeInimigos = contarInimigos();
 
        
        
        if (inimigosMortos >= 35 && totalChefesInvocados == 0 && numeroDeInimigos == 0) {
            tempoEspera--;
            if (tempoEspera == 0) {
                Chefe chefe4 = new Chefe4();
                addObject(chefe4, getWidth()-100, getHeight() - 220);
                totalChefesInvocados = 1;
            }
        }
        
        if (chefeMorto == 1) {
            passarFase();
        }*/
        
        try {
            final int totalInimigoAInvocar = 15;
            
            configurarJogadores();
            setBackground(fundoFase4);
            
            if (tempoComeçarFase > 0) tempoComeçarFase--;
            
            if (tempoComeçarFase == 0) {
                /*
                if (!dialogo) {
                    GifActor gifDialogo1 = new GifActor(locDialogo1, 800);
                    addObject(gifDialogo1, getWidth()/2, 65);
                    
                    dialogo = true;
                }*/
                
                tiposDeInimigo = new Inimigo[] {
                    new EspectroDoDesespero(),
                    new DroneMaluco(),
                    new Inimigo0(),
                    new Inimigo1()
                };
                
                gerenciarHorda(totalInimigoAInvocar, 3, tiposDeInimigo);
            }
            
            invocarChefe(new Chefe4(), totalInimigoAInvocar);
            
            if (chefeInvocado) {
                List<Chefe> qntChefeNoMundo = getObjects(Chefe.class);
                
                if (qntChefeNoMundo.isEmpty()) {
                    redefinirConfiguracoes();
                    passarFase();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int contarInimigos() {
        List<Inimigo> inimigos = getObjects(Inimigo.class);
        return inimigos.size();
    }
    
    public void inimigoDerrotado() {
        this.inimigosMortos++;
        //System.out.println("Inimigos Mortos: " + inimigosMortos);
    }
    
    public void chefeDerrotado() {
        this.chefeMorto++;
        // System.out.println("Boss Mortos: " + chefeMorto);
    }
    
    public void definirFaseAtual(int novaFase) 
    {
        this.faseAtual = novaFase;
    }
    
    public void gerenciarHorda(int qntTotalInvoc, int qntInvocPorEtapa, Inimigo[] tipoInimigo) {
        
        // Atenderá essa condição, se o total que eu quero invocar for maior que o total já invocado
        if (qntTotalInvoc > totalInimigoInvocEmHorda) {
        
            // O tempo só será reduzido que ele for maior que 0
            if (tempoEsperaInvocarInimigo > 0) tempoEsperaInvocarInimigo--;
            
            // Pega todos os inimigos do tipo Inimigo que estão presentes no mundo
            
             List<Inimigo> qntInimigoNoMundo = getObjects(Inimigo.class);
            // Atenderá essa condição, se a quantidade de inimigos no mundo
            // for menor que a quant que quero invocar na etapa
            if (qntInvocPorEtapa > qntInimigoNoMundo.size()) {
                horda(tipoInimigo);
            }
        }
    }
    
    public void horda(Inimigo[] tipoInimigo) 
    {
        // Será executado quando o tempo chegar a 0;
        if (tempoEsperaInvocarInimigo == 0) {
            Random random = new Random();
            
            // Pega um indice aleatório, com base no tamanho da lista de tipos de inimigos
            int indiceAleatorio = random.nextInt(tipoInimigo.length);
            
            addObject(tipoInimigo[indiceAleatorio], getWidth(), getHeight()-50);
            
            // Quando 1 inimigo é invocado, aumenta o total de inimigos invocados
            totalInimigoInvocEmHorda += 1;
            // Quando o inimigo for invocado, o intervalo entre as invocações voltará a ser 1s
            tempoEsperaInvocarInimigo = 60;
        }
    }
    
    public void invocarChefe(Chefe chefe, int totalInimigoAInvocar) {
        
        if (totalInimigoInvocEmHorda == totalInimigoAInvocar) {
            List<Inimigo> qntInimigoNoMundo = getObjects(Inimigo.class);
            
            if (qntInimigoNoMundo.isEmpty() && !chefeInvocado)
            {
                addObject(chefe, getWidth()-100, getHeight() - chefe.getImage().getHeight()/2);
                chefeInvocado = true;
            }
        }
    }
    
    public void teste() {
        
    }
    
    // Redefinir 'chefeInvocado' para false; TotalInimigoAInvocar = 0;
    // totalInimigoInvocEmHorda = 0; 
    
}