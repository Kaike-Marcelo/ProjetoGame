import greenfoot.*;

public class Jogador1 extends Jogador
{      
    public Jogador1(Coracao coracao) {
        super(coracao);
        
        configurarTeclas("A", "D", "W", "V", "R", "C");
        imagemJogadorDireita();
        imagemJogadorEsquerda();
    }
    
    public void imagemJogadorDireita() {
        definirImgJogadorDireita("Player1Estatico_Frente.gif", "Player1Correndo_Frente.gif");
    }
    
    public void imagemJogadorEsquerda() {
        definirImgJogadorEsquerda("Player1Estatico_Tras.gif", "Player1Correndo_Tras.gif");
    }
    
     public void act() {
        super.act();
        
        if (!verificaVida()) {
            getWorld().removeObject(this);
        }
    }
}
