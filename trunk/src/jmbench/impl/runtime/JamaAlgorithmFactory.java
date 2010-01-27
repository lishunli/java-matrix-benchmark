/*
 * Copyright (c) 2009-2010, Peter Abeles. All Rights Reserved.
 *
 * This file is part of JMatrixBenchmark.
 *
 * JMatrixBenchmark is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 *
 * JMatrixBenchmark is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JMatrixBenchmark.  If not, see <http://www.gnu.org/licenses/>.
 */

package jmbench.impl.runtime;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import Jama.SingularValueDecomposition;
import jmbench.impl.MatrixLibrary;
import jmbench.interfaces.AlgorithmInterface;
import jmbench.interfaces.LibraryAlgorithmFactory;
import org.ejml.data.DenseMatrix64F;


/**
 * @author Peter Abeles
 */
public class JamaAlgorithmFactory implements LibraryAlgorithmFactory {

    private static abstract class MyInterface implements AlgorithmInterface
    {
        @Override
        public String getName() {
            return MatrixLibrary.JAMA.getVersionName();
        }
    }

    @Override
    public AlgorithmInterface chol() {
        return new Chol();
    }

    public static class Chol extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                if( !matA.chol().isSPD() ) {
                    throw new RuntimeException("Is not SPD");
                }
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface lu() {
        return new LU();
    }

    public static class LU extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                matA.lu();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface svd() {
        return new SVD();
    }

    public static class SVD extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                SingularValueDecomposition s = matA.svd();
                s.getU();
                s.getS();
                s.getV();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface eigSymm() {
        return new Eig();
    }

    public static class Eig extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                EigenvalueDecomposition e = matA.eig();
                e.getD();
                e.getV();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface qr() {
        return new QR();
    }

    public static class QR extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                matA.qr();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface det() {
        return new Det();
    }

    public static class Det extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                matA.det();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface invert() {
        return new Inv();
    }

    public static class Inv extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            for( long i = 0; i < numTrials; i++ ) {
                matA.inverse();
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface add() {
        return new Add();
    }

    public static class Add extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);
            Matrix matB = convertToJama(inputs[1]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.plus(matB);
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface mult() {
        return new Mult();
    }

    public static class Mult extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);
            Matrix matB = convertToJama(inputs[1]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.times(matB);
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface multTransA() {
        return new MulTranA();
    }

    public static class MulTranA extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);
            Matrix matB = convertToJama(inputs[1]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.transpose().times(matB);
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface scale() {
        return new Scale();
    }

    public static class Scale extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.times(2.5);
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface solveExact() {
        return new Solve();
    }

    @Override
    public AlgorithmInterface solveOver() {
        return new Solve();
    }

    public static class Solve extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);
            Matrix matB = convertToJama(inputs[1]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.solve(matB);
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }

    @Override
    public AlgorithmInterface transpose() {
        return new Transpose();
    }

    public static class Transpose extends MyInterface {
        @Override
        public long process(DenseMatrix64F[]inputs, long numTrials) {
            Matrix matA = convertToJama(inputs[0]);

            long prev = System.currentTimeMillis();

            Matrix result = null;

            for( long i = 0; i < numTrials; i++ ) {
                result = matA.transpose();
            }

            if( result == null ) {
                System.out.println("asdasdasdasd");
            }

            return System.currentTimeMillis()-prev;
        }
    }
    
    public static Matrix convertToJama( DenseMatrix64F orig )
    {
        Matrix ret = new Matrix(orig.getNumRows(),orig.getNumCols());

        for( int i = 0; i < orig.numRows; i++ ) {
            for( int j = 0; j < orig.numCols; j++ ) {
                ret.set(i,j,orig.get(i,j));
            }
        }

        return ret;
    }

    public static DenseMatrix64F jamaToEjml( Matrix orig )
    {
        if( orig == null )
            return null;

        DenseMatrix64F ret = new DenseMatrix64F(orig.getRowDimension(),orig.getColumnDimension());

        for( int i = 0; i < ret.numRows; i++ ) {
            for( int j = 0; j < ret.numCols; j++ ) {
                ret.set(i,j,orig.get(i,j));
            }
        }

        return ret;
    }
}