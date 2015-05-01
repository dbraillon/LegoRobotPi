using Raspberry.IO.GeneralPurpose;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace LegoRobotPi
{
    class Program
    {
        static void Main(string[] args)
        {
            Console.WriteLine("Hello world !");

            ServerSocket<RobotDataHandler> serverSocket = new ServerSocket<RobotDataHandler>();
            serverSocket.Start();

            do
            {
                Thread.Sleep(1000);
            }
            while (Console.ReadLine() != "stop");

            serverSocket.Stop();

            Console.WriteLine("Stopped");
        }
    }
}
