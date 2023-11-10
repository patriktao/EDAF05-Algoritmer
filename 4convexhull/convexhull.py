

class Point:
     def __init__(self, x, y):
          self.x = x
          self.y = y


class ConvexHull:
     def __init__(self):
          self.points = []
          self.dim = 0
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
               self.points.append(Point(x, y))
     
     def run(self):
          self.graham_scan(self.points)
          self.preparata_hong()

     def graham_scan(self, points):
          n = points
          
          # index of the point with min y coordinate
          i = min(range(len(points)), key=lambda i: points[i].y)
          
          # Swap p0 and pi
          t = points[0]
          points[0] = points[i]
          points[i] = t
          
          #Subtract coordinates of t from every point
          
          for p in points:
               if t != p:
                    p.x -= t.x
                    p.y -= t.y
          #sort elements 1..n-1 of p by 





     def preparata_hong():




if __name__ == "__main__":
    ConvexHull()
