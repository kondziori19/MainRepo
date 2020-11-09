#include <iostream>
#include <fstream>
#include <string>
#include <cmath>
#include <vector>
#include <list>

using namespace std;

struct Cell {
    int x, y, parentx, parenty;
    double f, g, h;
};

double calculate_h(int x, int y, int dest_x, int dest_y ) {
    double h;

    h = sqrt((x - dest_x) * (x - dest_x) + (y - dest_y) * (y - dest_y));

    return h;
         
}

vector<vector<int>> openMapFile() {
    string line;
    ifstream map("grid.txt");
    vector<vector<int>> cells(20, vector<int>(20));

    if (map.is_open()) {
        while (!map.eof()) {

            for (int i = 0; i < 20; i++) {         //importing map to array
                for (int j = 0; j < 20; j++) {  
                    map >> cells[i][j];
                }
            }
        }
    }
    else
    {
        printf("couldnt open the file :(");
    }
    map.close();
    return cells;
}

void print2dVec(vector<vector<int>> vect) {
    for (int i = 0; i < 20; i++) {
        for (int j = 0; j < 20; j++) {
            cout << vect[i][j] << " ";
        }
        cout << "\n";
    }
}

bool isInRange(int x, int y) {
    if (x >= 0 && x < 20 && y >= 0 && y < 20) {
        return true;
    }
    else {
        return false;
    }
}


bool isBlocked(vector<vector<int>> map, int x, int y) {
    if (map[x][y] == 5) {
        return true;
    }
    else {
        return false;
    }
}

bool isDest(int x, int y, int dest_x, int dest_y) {
    if (x == dest_x && y == dest_y) {
        return true;
    }
    else {
        return false;
    }
}

Cell findLowestF(vector<Cell> list) {
    double lowF = list[0].f;
    int nr = 0;
    for (int i = 0; i < list.size(); i++) {
        if (list[i].f < lowF) {
            lowF = list[i].f;
            nr = i;
        }
    }
    return list[nr];
}

int findLowestFnr(vector<Cell> list) {
    double lowF = list[0].f;
    int nr = 0;
    for (int i = 0; i < list.size(); i++) {
        if (list[i].f < lowF) {
            lowF = list[i].f;
            nr = i;
        }
    }
    return nr;
}

void tracePath(vector<vector<int>> map, Cell cells[][20], int dest_x, int dest_y) {
    int x = dest_x;
    int y = dest_y;
    vector<Cell> path;

    while (!(cells[x][y].parentx==x && cells[x][y].parenty == y)) {

          path.push_back(cells[x][y]);
          int tmp_x = cells[x][y].parentx;
          int tmp_y = cells[x][y].parenty;
 
          x = tmp_x;
          y = tmp_y;

    }
    path.push_back(cells[x][y]);
   
    for (int i = 0; i < path.size(); i++) {
        map[path[i].x][path[i].y] = 3;
    }
    printf("\n");
    print2dVec(map);
    
    return;
}


void aStarAlgorithm(vector<vector<int>> map, int start_x, int start_y, int dest_x, int dest_y) {

    
    if (!isInRange(start_x, start_y) || !isInRange(dest_x, dest_y)){
        printf("Start cell or destination cell is out of range.\n"); 
            return;
   }

    if (isBlocked(map, start_x, start_y) == true || isBlocked(map, dest_x, dest_y) == true)
    {
        printf("Start cell or destination cell is blocked.\n"); // cell[x][y] == 5
        return;
    }

    if (start_x == dest_x && start_y == dest_y) {
        printf("Start cell and destination are placed on the same cell.\n"); 
        return;
    }

    Cell cells[20][20]; // array with details of a cells
    bool closedList[20][20];
    
    for (int x = 0; x < 20; x++) {              // creating cells array
        for (int y = 0; y < 20; y++) {
            cells[x][y].f = FLT_MAX;
            cells[x][y].g = FLT_MAX;
            cells[x][y].h = FLT_MAX;
            cells[x][y].parentx = -1;
            cells[x][y].parenty = -1;
            cells[x][y].x = x;
            cells[x][y].y = y;

            closedList[x][y] = false; // filling closedlist
        }
    }
          
            int i, j;

            // Initialising the parameters of the start
            i = start_x, j = start_y;
            cells[i][j].f = 0.0;
            cells[i][j].g = 0.0;
            cells[i][j].h = 0.0;
            cells[i][j].parentx = i;
            cells[i][j].parenty = j;
            cells[i][j].x = i;
            cells[i][j].y = j;

           

            vector<Cell> openList;
            openList.push_back(cells[i][j]); //adding starting cell to openList
            bool dest_found = false;

            while (!openList.empty()) {
                Cell q = findLowestF(openList); //q is the element of the open list with the lowest f value
                openList.erase(openList.begin() + findLowestFnr(openList)); //removing q from openlist

                i = q.x;
                j = q.y;
                closedList[i][j] = true; //adding q to the closed list.

                double gNew, hNew, fNew;

                    /*
                    UP(i - 1, j)
                    DOWN(i + 1, j)
                    RIGHT(i, j + 1)
                    LEFT(i, j - 1)
                    */
                //  UP

                if (isInRange((i - 1), j) == true) {
                    if (isDest((i - 1), j, dest_x, dest_y)) {
                        cells[i - 1][j].parentx = i;
                        cells[i - 1][j].parenty = j;
                        tracePath(map, cells, dest_x, dest_y);
                        dest_found = true;
                        return;
                    }
                    else if (closedList[i - 1][j] == false && isBlocked(map, i - 1, j) == false) { // N Cell isnt on the closedlist and isnt blocked
                        gNew = cells[i][j].g + 1.0; //g of parent + 1
                        hNew = calculate_h(i - 1, j, dest_x, dest_y);
                        fNew = gNew + hNew;

                        if (cells[i - 1][j].f == FLT_MAX || cells[i - 1][j].f > fNew) {

                            cells[i - 1][j].f = fNew;
                            cells[i - 1][j].g = gNew;
                            cells[i - 1][j].h = hNew;
                            cells[i - 1][j].parentx = i;
                            cells[i - 1][j].parenty = j;

                            openList.push_back(cells[i - 1][j]);

                        }
                    }
                }

                // DOWN
                if (isInRange((i + 1), j) == true) {
                    if (isDest(i + 1, j, dest_x, dest_y)) {
                        cells[i + 1][j].parentx = i;
                        cells[i + 1][j].parenty = j;
                        tracePath(map, cells, dest_x, dest_y);
                        dest_found = true;
                        return;
                    }
                    else if (closedList[i + 1][j] == false && isBlocked(map, i + 1, j) == false) { // N Cell isnt on the closedlist and isnt blocked
                        gNew = cells[i][j].g + 1.0; //g of parent + 1
                        hNew = calculate_h(i + 1, j, dest_x, dest_y);
                        fNew = gNew + hNew;

                        if (cells[i + 1][j].f == FLT_MAX || cells[i + 1][j].f > fNew) {

                            cells[i + 1][j].f = fNew;
                            cells[i + 1][j].g = gNew;
                            cells[i + 1][j].h = hNew;
                            cells[i + 1][j].parentx = i;
                            cells[i + 1][j].parenty = j;

                            openList.push_back(cells[i + 1][j]);

                        }
                    }
                }

                // LEFT
                if (isInRange(i, (j - 1)) == true) {
                    if (isDest(i, j - 1, dest_x, dest_y)) {
                        cells[i][j - 1].parentx = i;
                        cells[i][j - 1].parenty = j;
                        tracePath(map, cells, dest_x, dest_y);
                        dest_found = true;
                        return;
                    }
                    else if (closedList[i][j - 1] == false && isBlocked(map, i, j - 1) == false) { // N Cell isnt on the closedlist and isnt blocked
                        gNew = cells[i][j].g + 1.0; //g of parent + 1
                        hNew = calculate_h(i, j - 1, dest_x, dest_y);
                        fNew = gNew + hNew;

                        if (cells[i][j - 1].f == FLT_MAX || cells[i][j - 1].f > fNew) {

                            cells[i][j - 1].f = fNew;
                            cells[i][j - 1].g = gNew;
                            cells[i][j - 1].h = hNew;
                            cells[i][j - 1].parentx = i;
                            cells[i][j - 1].parenty = j;

                            openList.push_back(cells[i][j - 1]);

                        }
                    }
                }

                // RIGHT
                if (isInRange(i, (j + 1)) == true) {
                    if (isDest(i, j + 1, dest_x, dest_y)) {
                        cells[i][j + 1].parentx = i;
                        cells[i][j + 1].parenty = j;
                        tracePath(map, cells, dest_x, dest_y);
                        dest_found = true;
                        return;
                    }
                    else if (closedList[i][j + 1] == false && isBlocked(map, i, j + 1) == false) { // N Cell isnt on the closedlist and isnt blocked
                        gNew = cells[i][j].g + 1.0; //g of parent + 1
                        hNew = calculate_h(i, j + 1, dest_x, dest_y);
                        fNew = gNew + hNew;

                        if (cells[i][j + 1].f == FLT_MAX || cells[i][j + 1].f > fNew) {

                            cells[i][j + 1].f = fNew;
                            cells[i][j + 1].g = gNew;
                            cells[i][j + 1].h = hNew;
                            cells[i][j + 1].parentx = i;
                            cells[i][j + 1].parenty = j;

                            openList.push_back(cells[i][j + 1]);

                        }
                    }
                }
            }
            if (dest_found == false)
                printf("Failed to find the Destination Cell\n");

            return;
}

int main()
{
    
    int start_x = 19; 
    int start_y = 0;  
    int dest_x = 0;
    int dest_y = 19;


    vector < vector<int>> grid = openMapFile();

    print2dVec(grid);
    printf("\n---------------------------------------\n");
    aStarAlgorithm(grid, start_x, start_y, dest_x, dest_y);

}

