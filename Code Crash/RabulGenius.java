import greenfoot.*;
import java.util.List;

public class RabulGenius extends Chefe {
    
    private static int vida = 500;
    private static int forcaDano = 2;
    private int velocidadeX = 10;
    private int qntInimigosADerrotar = 2;
    private int tempo = 600;
    private int chancesInvocarInimigos = 4;
    
    public RabulGenius() {
        super(vida, forcaDano);
        
        definirVelocidade(velocidadeX);
        definirTempoDeEspera(tempo);
        controlarInvocacao(qntInimigosADerrotar, chancesInvocarInimigos);
    }
    
    public void act() {
        super.act();
    }
}