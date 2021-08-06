package com.aim.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Application {

	public static void main(String[] args) {
		System.out.println("Application Startup Reached");
		// scanCsv("/home/aim/Downloads/CONTRACTOR LIST 2018 NCA.csv");
		// writeToCsv();
		String filePath = "/home/aim/Downloads/nyayo-owners-P1.csv";
		int[] nums = new int[] { 2, 3, 4 };
		int nameInd = 0;
		int emailInd = 6;
		int skipRows = 1;

		List<List<String>> data = scanCsv(filePath, nums, nameInd, emailInd, skipRows);

		try {
			writeToCsv(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param filePath
	 */
	private static List<List<String>> scanCsv(String filePath, int[] numIndex, int nameIndex, int emailIndex,
			int skipRows) {
		List<List<String>> output = new ArrayList<>();
		File csvFile = new File(filePath);
		String row;
		// num of rows to skip
		int count = 1;

		if (csvFile.isFile()) {
			System.out.println("reached");
			try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFile))) {
				while ((row = csvReader.readLine()) != null) {
					System.out.println(row);
					if (count <= skipRows) {
						count++;
						continue;
					}

					List<String> rowEntry = new ArrayList<>();
					List<String> number = new ArrayList<>();
					String name = "";
					String email = "";
					String[] data = row.split(",");
					// System.out.println(data[3]);

					if (numIndex.length > 0) {
						for (int i = 0; i < numIndex.length; i++) {
							String msisdn = numIndex[i] > (data.length - 1) ? "" : formatNumber(data[numIndex[i]]);
							if (!msisdn.isEmpty()) {
								number.add(msisdn);
							}
						}
					}

					if (nameIndex != -1 && data.length - 1 >= nameIndex) {
						name = data[nameIndex];
					}

					if (emailIndex != -1 && data.length - 1 >= emailIndex) {
						email = data[emailIndex];
					}

					if (number.size() == 0) {
						continue;
					}

					for (String msisdn : number) {
						rowEntry.add(msisdn);
						rowEntry.add(name);
						rowEntry.add(email);
						output.add(rowEntry);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return output;
	}

	private static void writeToCsv(List<List<String>> data) throws IOException {
		try (FileWriter csvWriter = new FileWriter("nyayo-owners-P1.csv")) {

			csvWriter.append("number");
			csvWriter.append(",");
			csvWriter.append("fullname");
			csvWriter.append(",");
			csvWriter.append("email");
			csvWriter.append("\n");

			data.forEach(entry -> {
				try {
					csvWriter.append(entry.get(0));
					csvWriter.append(",");
					csvWriter.append(entry.get(1));
					csvWriter.append(",");
					csvWriter.append(entry.get(2));
					csvWriter.append("\n");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});

			csvWriter.flush();
			// csvWriter.close();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	private static String formatNumber(String num) {

		System.out.println(num);

		String number;

		if (num.contains("/")) {
			number = num.split("/")[0];
		} else if (num.contains(",")) {
			number = num.split(",")[0];
		} else {
			number = num;
		}

		// remove all white space
		number = number.replace("\\s", "");

		number = number.replaceAll("[^a-zA-Z0-9]", "");
		number = number.replaceAll("[a-zA-Z]", "");

		if (number.startsWith("+254") && number.split("").length == 12) {
			StringBuilder builder = new StringBuilder();
			builder.append(number);
			builder.deleteCharAt(0);
			String output = builder.toString();
			if (output.split("").length == 12) {
				return output;
			} else {
				return "";
			}
		}

		if (number.startsWith("254") && number.split("").length == 12) {
			return number;
		}

		if (number.startsWith("07")) {
			StringBuilder builder = new StringBuilder();
			builder.append(number);
			builder.deleteCharAt(0);
			String output = "254" + builder.toString();
			if (output.split("").length == 12) {
				return output;
			} else {
				return "";
			}
		}

		if (number.startsWith("7")) {
			String output = "254" + number;
			if (output.split("").length == 12) {
				return output;
			} else {
				return "";
			}
		}
		return "";
	}

}
