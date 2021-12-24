package ru.vsu.cs.math;

/**
 * Действия с матрицей
 */

public class Matrix3Turning {
    private float[][] matrix;

    /**
     * Конструктор матрицы
     */

    public Matrix3Turning() {
        this.matrix = new float[3][3];
    }

    /**
     *Получение угла по векторам
     */

    public static float getAngleOY(Vector3 v1, Vector3 v2) {
        if (v1.equals(v2)) return 0;

        float dx = Math.abs(v1.getX() - v2.getX());
        float dy = Math.abs(v1.getY() - v2.getY());
        float dz = Math.abs(v1.getZ() - v2.getZ());

        return (float) Math.acos(dx / Math.sqrt(dx * dx + dz * dz));
    }

    public static float getAngleOX(Vector3 v1, Vector3 v2) {
        if (v1.equals(v2)) return 0;

        float dx = Math.abs(v1.getX() - v2.getX());
        float dy = Math.abs(v1.getY() - v2.getY());
        float dz = Math.abs(v1.getZ() - v2.getZ());

        return (float) Math.acos(dy / Math.sqrt(dz * dz + dy * dy));
    }

    /**
     *Вращение спирали (поворот относительно x, y, z)
     */

    public static Vector3 rotationOnX(Vector3 v, float angle) {
        float newX = v.getX();
        float newY = (float) (v.getY() * Math.cos(angle) - v.getZ() * Math.sin(angle));
        float newZ = (float) (v.getY() * Math.sin(angle) + v.getZ() * Math.cos(angle));

        return new Vector3(newX, newY, newZ);
    }

    public static Vector3 rotationOnY(Vector3 v, float angle) {
        float newX = (float) (v.getX() * Math.cos(angle) - v.getZ() * Math.sin(angle));
        float newY = v.getY();
        float newZ = (float) (v.getX() * Math.sin(angle) + v.getZ() * Math.cos(angle));

        return new Vector3(newX, newY, newZ);
    }

    public static Vector3 rotationOnZ(Vector3 v, float angle) {
        float newX = (float) (v.getX() * Math.cos(angle) - v.getY() * Math.sin(angle));
        float newY = (float) (v.getX() * Math.sin(angle) + v.getY() * Math.cos(angle));
        float newZ = v.getZ();

        return new Vector3(newX, newY, newZ);
    }

}
