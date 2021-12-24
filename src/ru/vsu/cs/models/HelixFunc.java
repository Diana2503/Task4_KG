package ru.vsu.cs.models;

import ru.vsu.cs.functions.DefaultFunction;
import ru.vsu.cs.functions.IFunction;
import ru.vsu.cs.math.Matrix3Turning;
import ru.vsu.cs.math.Vector3;
import ru.vsu.cs.third.IModel;
import ru.vsu.cs.third.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Построение спирали
 */

public class HelixFunc implements IModel {

    private int countOfTurns;
    private int countOfPointsPerTurn;
    private float radius = 0.6f;
    private float step;
    private float thickness;
    private int countOfPointsPerTick;
    IFunction radiusFunc = new DefaultFunction();
    IFunction stepFunc;

    public HelixFunc(int countOfTurns, int countOfPointsPerTurn, float step, float thickness,
                     int countOfPointsPerTick, IFunction stepFunc) {
        this.countOfTurns = countOfTurns;
        this.countOfPointsPerTurn = countOfPointsPerTurn;
        this.step = step;
        this.thickness = thickness;
        this.countOfPointsPerTick = countOfPointsPerTick;
        this.stepFunc = stepFunc;
    }

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        Vector3[] carcass = new Vector3[countOfTurns * (countOfPointsPerTurn + 1)];

        float currentZ = 0;
        float currentX;
        float currentY;
        float currentRad = 0;

        int counter = 0;

        float radIncr = (float) (2 * Math.PI / countOfPointsPerTurn);

        float zIncr = step / countOfPointsPerTurn;
        float zConstIncr = thickness * 2 / countOfPointsPerTurn;
        float percent;

        for (int i = 0; i < this.countOfTurns; i++) {
            for (int j = 0; j <= countOfPointsPerTurn; j++) {
                percent = (float) (i * countOfPointsPerTurn + j) / carcass.length;
                currentX = (float) (Math.cos(currentRad) * radius) * radiusFunc.complete(percent) + thickness;
                currentY = (float) (Math.sin(currentRad) * radius) * radiusFunc.complete(percent) + thickness;

                carcass[counter] = new Vector3(currentX, currentY, currentZ);
                currentRad += radIncr;
                currentZ += zIncr * stepFunc.complete(percent) + zConstIncr;
                counter++;
            }
        }

        Vector3[][] section = new Vector3[carcass.length][countOfPointsPerTick];

        radIncr = (float) (2 * Math.PI / countOfPointsPerTick);

        float angleX = Matrix3Turning.getAngleOX(carcass[0], carcass[1]);
        float angleY = Matrix3Turning.getAngleOY(carcass[0], carcass[1]);

        float angleZ = 0;
        float angleZIncr = (float) (Math.PI * 2 / (carcass.length / countOfTurns - 1));
        Vector3 temp;

        for (int i = 0; i < section.length; i++) {
            currentRad = 0;

            for (int j = 0; j < countOfPointsPerTick; j++) {
                currentX = (float) Math.sin(currentRad) * thickness;
                currentY = 0;
                currentZ = (float) Math.cos(currentRad) * thickness;

                temp = new Vector3(currentX, currentY, currentZ);

                temp = Matrix3Turning.rotationOnY(temp, angleY);
                temp = Matrix3Turning.rotationOnX(temp, angleX);
                temp = Matrix3Turning.rotationOnZ(temp, angleZ);

                section[i][j] = new Vector3(temp.getX() + carcass[i].getX(), temp.getY() + carcass[i].getY(),
                        temp.getZ() + carcass[i].getZ());

                currentRad += radIncr;
            }

            angleZ += angleZIncr;
        }

        for (int i = 0; i < section.length - 1; i++) {
            for (int j = 0; j < section[i].length - 1; j++) {

                lines.add(new PolyLine3D(Arrays.asList(new Vector3(section[i][j].getX(), section[i][j].getY(), section[i][j].getZ()),
                        new Vector3(section[i + 1][j].getX(), section[i + 1][j].getY(), section[i + 1][j].getZ()),
                        new Vector3(section[i + 1][j + 1].getX(), section[i + 1][j + 1].getY(), section[i + 1][j + 1].getZ()),
                        new Vector3(section[i][j + 1].getX(), section[i][j + 1].getY(), section[i][j + 1].getZ())), true));
            }
            lines.add(new PolyLine3D(Arrays.asList(new Vector3(section[i][0].getX(), section[i][0].getY(), section[i][0].getZ()),
                    new Vector3(section[i + 1][0].getX(), section[i + 1][0].getY(), section[i + 1][0].getZ()),
                    new Vector3(section[i + 1][section[i].length - 1].getX(), section[i + 1][section[i].length - 1].getY(), section[i + 1][section[i].length - 1].getZ()),
                    new Vector3(section[i][section[i].length - 1].getX(), section[i][section[i].length - 1].getY(), section[i][section[i].length - 1].getZ())), true));
        }
        return lines;
    }
}