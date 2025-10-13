import pandas as pd
import mysql.connector

#creating the connection
connection = mysql.connector.connect(host="localhost",user="root",password="",database="sports_stats")

#creating the cursor that allows us to execute statements
cursor = connection.cursor()

#reading the data from the csv files
matches=pd.read_csv("matches.csv")
players=pd.read_csv("players.csv")
player_stats=pd.read_csv("player_stats.csv")

#writing into the matches table
for _, row in matches.iterrows():
    cursor.execute("""insert into matches values(%s,%s,%s,%s)""",(row['match_id'],row['date'],row['home_team'],row['away_team']))

#writing into the matches players
for _, row in players.iterrows():
    cursor.execute("""insert into players values(%s,%s,%s,%s,%s)""",(row['player_id'],row['first_name'],row['last_name'],row['team'],row['position']))

#writing into the matches player_stats
for _, row in player_stats.iterrows():
    cursor.execute("""insert into player_stats values(%s,%s,%s,%s,%s,%s)""",(row['stat_id'],row['match_id'],row['player_id'],row['goals'],row['assists'],row['fouls']))

#saving and closing the connection
connection.commit()
cursor.close()
connection.close()

print("Data successfully entered into all tables")
