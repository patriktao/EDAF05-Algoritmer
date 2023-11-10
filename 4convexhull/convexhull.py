

from math import atan2

class ConvexHull:
     def __init__(self):
          self.points = []
          self.dim = 0
          self.anges = []
          self.parser()
          self.run()

     def parser(self):
          first_line = input().split()
          self.dimdim = int(first_line[0])
          num_points = int(first_line[1])
          for i in range(num_points):
               line = input().split()
               x = line[3]
               y = line[4]
               self.points.append((x, y))
     
     def run(self):
          self.graham_scan(self.points)
          self.preparata_hong()
          
     def direction(p1, p2, p3):
          # Function to determine the direction of the turn formed by three points
          # Returns "left", "right", or "straight"
          cross_product = (p2[1] - p1[1]) * (p3[0] - p2[0]) - (p2[0] - p1[0]) * (p3[1] - p2[1])
          if cross_product > 0:
               return "left"
          elif cross_product < 0:
               return "right"
          else:
               return "straight"
     
     def graham_scan(self):
          n = len(self.points)
          
          # index of the point with min y coordinate
          i = min(range(len(self.points)), key=lambda i: self.points[i][1])
          
          # Swap p0 and pi
          t = self.points[0]
          self.points[0] = self.points[i]
          self.points[i] = t
          
          #Subtract coordinates of t from every point
          for p in self.points:
               if t != p:
                    p[0] -= t[0]
                    p[1] -= t[1]
          
          #sort elements 1..n-1 of p by 
          print(self.points)
          sorted_p = sorted(self.points[1:], key=lambda point: atan2(point[1] - self.points[0][1], point[0] - self.points[0][0]), reverse="True")
          
          # Graham Scan Algorithm
          hull_stack = [self.points[0], self.points[1], self.points[2]]
          
          for k in range(2, n - 1):
               pt = sorted_p[k]
               
               #while the direction is not left
               while len(hull_stack) > 1 and direction(hull_stack[-2], hull_stack[-1], pt) != "left":
                    hull_stack.pop()
               
               #Push the current point onto the stack
               hull_stack.append(pt)
          
          return hull_stack
          
     

     def preparata_hong():
          print("")




if __name__ == "__main__":
    ConvexHull()
