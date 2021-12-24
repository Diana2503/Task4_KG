package ru.vsu.cs.functions;

public class ExpFunction implements IFunction{

    /**
     * Функция экспоненты
     */

    @Override
    public float complete(float percent) {
        return (float)Math.abs(Math.sin(percent * Math.PI));
    }
}
