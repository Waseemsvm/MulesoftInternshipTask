package net.sqliteApplication;

import java.util.Scanner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;

public class Task1{
	
	private Connection connect(){
			String url = "jdbc:sqlite:C://App/db/movie.db";
			Connection conn = null;
			System.out.println("Connecting to movies Database...\n");
			
			try{
				conn = DriverManager.getConnection(url);
				System.out.println("Connection Established\n");
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
			
			
			return conn;
			
	}
	
	public void insert(String movie_name, String actor_name, String director_name, int year){
			String sql = "INSERT INTO Movies (name, actor, director, year_of_release) VALUES (?, ?, ?, ?)";
			try(Connection conn = this.connect();
				PreparedStatement pstmt = conn.prepareStatement(sql);){
				
				pstmt.setString(1, movie_name);
				pstmt.setString(2, actor_name);
				pstmt.setString(3, director_name);
				pstmt.setInt(4, year);
				pstmt.executeUpdate();
				
			
				
			}catch(SQLException e){
				System.out.println(e.getMessage());
			}
	}
	
	public void retrieveAll(){
		String sql = "SELECT * FROM Movies";
		
		try(Connection conn = this.connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			System.out.println("------------------------------------------------------------------------");	
			System.out.println("name" + "\t"
									+ "actor" + "\t"
									+ "director"+"\t"
									+ "year_of_release");
			System.out.println("------------------------------------------------------------------------");
									
			while(rs.next()){
				System.out.println(rs.getString("name") + "\t"
									+ rs.getString("actor") + "\t"
									+ rs.getString("director")+"\t\t"
									+rs.getInt("year_of_release"));
			System.out.println("------------------------------------------------------------------------");
			}
			
			
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void retrieve(String name){
		String sql = "SELECT * FROM Movies where name = '"+name+"'; ";
		
		try(Connection conn = this.connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)){
			
			System.out.println("------------------------------------------------------------------------");
				
			System.out.println("name" + "\t"
									+ "actor" + "\t"
									+ "director"+"\t"
									+ "year_of_release");
									
			System.out.println("------------------------------------------------------------------------");
									
			while(rs.next()){
				System.out.println(rs.getString("name") + "\t"
									+ rs.getString("actor") + "\t"
									+ rs.getString("director")+"\t"
									+rs.getInt("year_of_release"));
				System.out.println("------------------------------------------------------------------------");
			}
			
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	
			
	public static void main(String[] args){
		int option, year;
		String movie_name, actor_name, director_name, name;
		Scanner sc = new Scanner(System.in);
		Task1 app = new Task1();
		Connection connection = app.connect();
		do{
			System.out.println("\n1. Insert Data into Movies table\n2. Retrieve Details of the Movies\n3. Exit");
			option = sc.nextInt();
			switch(option){
				case 1: 
						System.out.println("Enter the name of the movie : ");movie_name = sc.nextLine();
						System.out.println("Enter the name of the actor : ");actor_name = sc.nextLine();
						System.out.println("Enter the name of the director : ");director_name = sc.nextLine();
						System.out.println("Enter the year of release : ");year = sc.nextInt();
						
						app.insert(movie_name, actor_name, director_name, year);
						System.out.println("inserted ( " + movie_name+ ", "+ actor_name + ", "+ year + ", "+ director_name+" ) into the movies table");
						
						
				break;
				case 2: 
						System.out.println("\n1.Retrieve the details of all movies\n2. Enter the movie name to get details");
						option = sc.nextInt();
						switch(option){
							case 1: app.retrieveAll();
							break;
							case 2: System.out.println("Enter the name of the movie you want to retrieve");
									name = sc.nextLine();
									app.retrieve(name);
							break;
							default: System.out.println("Enter a valid option");
						}
						
				break;
				case 3: try{
						
							if(connection != null){
								connection.close();
							}	
						}catch(SQLException e){
							System.err.println(e.getMessage());
						}
						System.exit(0);
				default:
					System.out.println("Please enter a valid option.");
			}
		}while(true);
		
	}
}