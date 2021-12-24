package ru.vsu.cs.functions;

public class AtanFunction implements IFunction{

    /**
     *Функция арктангенса 
     */
    @Override
    public float complete(float percent) {
        return (float) Math.abs(Math.atan(percent * Math.PI));
    }
}
