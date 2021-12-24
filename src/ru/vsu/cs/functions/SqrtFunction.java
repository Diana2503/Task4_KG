package ru.vsu.cs.functions;

public class SqrtFunction implements IFunction{

    /**
     * Функция корня
     */

    @Override
    public float complete(float percent) {
        return (float) Math.sqrt(percent * Math.PI);
    }
}
