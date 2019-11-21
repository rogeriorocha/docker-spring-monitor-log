package br.gov.mg.bdmg.fs.util.base;

public interface NumericalConstants {
	interface Base {
		interface Decimal{
			String NAME = "decimal";
			Integer VALUE = 10;
			Integer ZERO = 0;
			Integer ONE = 1;
			Integer TWO = 2;
			Integer THREE = 3;
			Integer FOUR = 4;
			Integer FIVE = 5;
		}
		interface Hexadecimal{
			String NAME = "hexadecimal";
			Integer VALUE = 16;
		}
	}
}
