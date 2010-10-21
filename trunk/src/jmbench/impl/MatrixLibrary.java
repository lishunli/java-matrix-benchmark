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

package jmbench.impl;

import jmbench.tools.EvaluationTarget;

import java.io.Serializable;
import java.lang.reflect.Field;


/**
 * Information on a linear algebra library, such as; its name, where its located, and how to load it.
 *
 * @author Peter Abeles
 */
@SuppressWarnings({"unchecked"})
public class MatrixLibrary implements Serializable {
    public static final MatrixLibrary EJML = new MatrixLibrary("EJML","ejml","ejml",AllLibraryVersion.EJML.class,false,false,0);
    public static final MatrixLibrary JAMA = new MatrixLibrary("JAMA","jama","jama",AllLibraryVersion.JAMA.class,false, false, 1);
    public static final MatrixLibrary MTJ = new MatrixLibrary("MTJ","mtj","mtj",AllLibraryVersion.MTJ.class,true, false, 2);
    public static final MatrixLibrary SEJML = new MatrixLibrary("SEJML","sejml","sejml",AllLibraryVersion.EJML.class,true, false, 3);
    public static final MatrixLibrary CM = new MatrixLibrary("CommMath","commons-math", "commons-math",AllLibraryVersion.COMMONS.class,true, false, 4);
    public static final MatrixLibrary OJALGO = new MatrixLibrary("ojAlgo","ojalgo","ojalgo",AllLibraryVersion.OJALGO.class,true, false, 6);
    public static final MatrixLibrary COLT = new MatrixLibrary("Colt","colt","colt",AllLibraryVersion.Colt.class,true, false, 7);
    public static final MatrixLibrary PCOLT = new MatrixLibrary("PColt","parallelcolt","parallelcolt",AllLibraryVersion.PColt.class,true, false, 8);
    public static final MatrixLibrary UJMP = new MatrixLibrary("UJMP","ujmp","ujmp",AllLibraryVersion.UJMP.class,true, true, 9);
    public static final MatrixLibrary UJMP_JAVA = new MatrixLibrary("UJMP-J","ujmp","ujmp-java",AllLibraryVersion.UJMP.class,true,false, 11);
    public static final MatrixLibrary JBLAS = new MatrixLibrary("JBLAS","jblas","jblas",AllLibraryVersion.JBLAS.class,true, true, 10);

    public String plotName;
    // directory that it loads its libraries from
    public String libraryDirName;
    // directory the results are saved into
    public String saveDirName;
    // Class that contains version information on the library
    public Class<LibraryVersion> versionClass;
    // does the slave need to load additional libraries
    public boolean extraLibs;
    // if the library is native or not
    public boolean nativeCode;

    // when plotted what color and stroke should be used
    public int plotLineType;

    public MatrixLibrary(String plotName, String libraryDirName, String saveDirName ,
                         Class<?> versionClass,
                         boolean extraLibs, boolean nativeCode, int plotLineType)
    {
        this.plotName = plotName;
        this.versionClass = (Class<LibraryVersion>)versionClass;
        this.libraryDirName = libraryDirName;
        this.saveDirName = saveDirName;
        this.extraLibs = extraLibs;
        this.nativeCode = nativeCode;
        this.plotLineType = plotLineType;
    }

    public MatrixLibrary() {

    }

    public static MatrixLibrary lookup( String libraryPlotName ) {
        Field[] fields = MatrixLibrary.class.getFields();

        for( Field f : fields ) {
            if( MatrixLibrary.class.isAssignableFrom(f.getType())) {
                try {
                    MatrixLibrary l = (MatrixLibrary)f.get(null);

                    if( l.plotName.compareToIgnoreCase(libraryPlotName) == 0 )
                        return l;
                } catch (IllegalAccessException e) {

                }
            }
        }

        return null;
    }

    public void addVersionInfo( EvaluationTarget target ) {
        try {
            LibraryVersion v = versionClass.newInstance();
            target.setVersion(v.getVersionString());
            target.setModificationData(v.getReleaseDate());
            return;
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {}

        target.setVersion("VersionInfo didn't load");
        target.setModificationData("Unknown");
    }

    public String getNameWithVersion() {
        try {
            LibraryVersion v = versionClass.newInstance();
            return plotName+" "+v.getVersionString();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {}

        return plotName;
    }

    public String getPlotName() {
        return plotName;
    }

    public void setPlotName(String plotName) {
        this.plotName = plotName;
    }

    public String getLibraryDirName() {
        return libraryDirName;
    }

    public void setLibraryDirName(String libraryDirName) {
        this.libraryDirName = libraryDirName;
    }

    public boolean isExtraLibs() {
        return extraLibs;
    }

    public void setExtraLibs(boolean extraLibs) {
        this.extraLibs = extraLibs;
    }

    public int getPlotLineType() {
        return plotLineType;
    }

    public void setPlotLineType(int plotLineType) {
        this.plotLineType = plotLineType;
    }

    public boolean isNativeCode() {
        return nativeCode;
    }

    public void setNativeCode(boolean nativeCode) {
        this.nativeCode = nativeCode;
    }

    public String getSaveDirName() {
        return saveDirName;
    }

    public void setSaveDirName(String saveDirName) {
        this.saveDirName = saveDirName;
    }
}
