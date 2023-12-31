import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/*
 *  Efeito é uma classe que representa um efeito visual que pode ser adicionado ao mundo.
 *  É possível definir uma animação para o efeito, e ele será removido automaticamente
 *  após a animação terminar.
 */

public class Efeito extends ObjetoAnimado
{
    public void act()
    {
        if (animacaoTerminou()) remover();
    }
    
    public void remover () {
        if (getWorld() != null) {
            getWorld().removeObject(this);
        }
    }
    /*
    public void setAnimacaoAtual (GreenfootImage[] anim) {
        super.setAnimacaoAtual(anim);
    } 
    
    public void animar () {
        super.animar();
    }
    
    public boolean animacaoTerminou () {
        return super.animacaoTerminou();
    }
    */
}
