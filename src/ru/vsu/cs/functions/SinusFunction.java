package ru.vsu.cs.functions;

public class SinusFunction implements IFunction{

    /**
     * Функция синуса
     */
    @Override
    public float complete(float percent) {
        float rad = (float)Math.PI * percent;
        return (float)Math.abs(Math.sin(rad));
    }
}
