using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace LegoRobotPi
{
    public class ServerSocket<T> where T : IDataHandler, new()
    {
        private bool IsRunning = false;
        private Thread ServerThread;
        private TcpListener CurrentServerSocket;
        private TcpClient CurrentClientSocket;

        public ServerSocket()
        {
            ServerThread = new Thread(Loop);
        }

        public void Start()
        {
            IsRunning = true;

            IPHostEntry ipHostInfo = Dns.GetHostEntry(Dns.GetHostName());
            IPAddress ipAddress = ipHostInfo.AddressList[0];

            CurrentServerSocket = new TcpListener(new IPEndPoint(ipAddress, 1337));
            CurrentServerSocket.Start(1);

            ServerThread.Start();
        }

        public void Stop()
        {
            IsRunning = false;

            ServerThread.Interrupt();

            if (CurrentClientSocket != null && CurrentClientSocket.Connected) CurrentClientSocket.Close();
            CurrentServerSocket.Stop();
        }

        private void Loop()
        {
            while (IsRunning)
            {
                T dataHandler = new T();

                try
                {
                    Console.WriteLine("Listen for incoming connection.");
                    CurrentClientSocket = CurrentServerSocket.AcceptTcpClient();
                    CurrentClientSocket.ReceiveTimeout = 10000;
                    NetworkStream stream = CurrentClientSocket.GetStream();

                    if (CurrentClientSocket != null)
                    {
                        Console.WriteLine("Client connected.");
                        string receivedData = null;

                        while (receivedData != "-1" && receivedData != "<EOF>" && receivedData != "stop" && receivedData != "")
                        {
                            byte[] bytes = new byte[1024];
                            
                            int bytesLength = stream.Read(bytes, 0, bytes.Length);
                            receivedData = Encoding.ASCII.GetString(bytes, 0, bytesLength);

                            dataHandler.HandleIncomingData(receivedData);
                        }

                        CurrentClientSocket.Close();
                    }
                    else
                    {
                        Console.WriteLine("Stop listening.");
                        throw new SocketException();
                    }

                    Thread.Sleep(1000);
                }
                catch (SocketException e)
                {
                    Console.WriteLine(e.ToString());
                }
                catch (Exception e)
                {
                    // TODO: Handle exception
                    Console.WriteLine(e.ToString());
                }

                dataHandler.Dispose();
            }
        }
    }
}
