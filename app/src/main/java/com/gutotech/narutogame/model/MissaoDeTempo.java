package com.gutotech.narutogame.model;

import com.gutotech.narutogame.myinterface.MissaoAceitaListener;

import java.io.Serializable;

public class MissaoDeTempo extends Missao implements Serializable {
    private long millisDuracao;
    private int expRecompensa;
    private double ryousRecompensa;

    public MissaoDeTempo() {
    }

    public MissaoDeTempo(int id, String titulo, String descricao, String graduacao, int level, String rank, String dataConcluida, String horarioConcluido, MissaoAceitaListener listener, long millisDuracao, int expRecompensa, double ryousRecompensa) {
        super(id, titulo, descricao, graduacao, level, rank, dataConcluida, horarioConcluido, listener);
        this.millisDuracao = millisDuracao;
        this.expRecompensa = expRecompensa;
        this.ryousRecompensa = ryousRecompensa;
    }

    public long getMillisDuracao() {
        return millisDuracao;
    }

    public void setMillisDuracao(long millisDuracao) {
        this.millisDuracao = millisDuracao;
    }

    public int getExpRecompensa() {
        return expRecompensa;
    }

    public void setExpRecompensa(int expRecompensa) {
        this.expRecompensa = expRecompensa;
    }

    public double getRyousRecompensa() {
        return ryousRecompensa;
    }

    public void setRyousRecompensa(double ryousRecompensa) {
        this.ryousRecompensa = ryousRecompensa;
    }
}
