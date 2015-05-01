using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace LegoRobotPi.Network
{
    public class ServerTcpClient
    {
        private TcpClient TcpClient;
        private NetworkStream TcpClientStream;
        private Thread TcpClientThread;
        private bool IsRunning;

        public ServerTcpClient(TcpClient tcpClient)
        {
            TcpClient = tcpClient;
            TcpClientStream = tcpClient.GetStream();
            TcpClientThread = new Thread(Listen);
            IsRunning = false;

            TcpClient.ReceiveTimeout = 1000;
        }

        public void Listen()
        {
            IsRunning = true;

            while (IsRunning)
            {
                try
                {
                    byte byteReceived = (byte)TcpClientStream.ReadByte();
                    ServerTcpMessage messageReceived = (ServerTcpMessage)byteReceived;

                    HandleMessage(messageReceived);
                }
                catch (IOException e)
                {
                    // If the ReceiveTimeout is reached an IOException will be raised
                    // with an InnerException of type SocketException and ErrorCode 10060
                    var socketException = e.InnerException as SocketException;
                    if (socketException == null || socketException.ErrorCode != 10060)
                    {
                        throw e;
                    }

                    Close();
                }
            }
        }

        public void HandleMessage(ServerTcpMessage messageReceived)
        {
            if (messageReceived.HasFlag(ServerTcpMessage.Close))
            {
                Close();
            }

            if (messageReceived.HasFlag(ServerTcpMessage.Forward))
            {

            }

            if (messageReceived.HasFlag(ServerTcpMessage.Backward))
            {

            }

            if (messageReceived.HasFlag(ServerTcpMessage.Left))
            {

            }

            if (messageReceived.HasFlag(ServerTcpMessage.Right))
            {

            }

            if (messageReceived.HasFlag(ServerTcpMessage.StopGo))
            {

            }

            if (messageReceived.HasFlag(ServerTcpMessage.StopTurn))
            {

            }
        }

        public void AcceptAndStart()
        {
            // Send accept and start message
            ServerTcpMessage acceptAndStart = ServerTcpMessage.Accept | ServerTcpMessage.Start;
            TcpClientStream.WriteByte((byte)acceptAndStart);
            TcpClientStream.Flush();

            // Start listening client
            TcpClientThread.Start();
        }

        public void RefuseAndClose()
        {
            // Send refuse and close message
            ServerTcpMessage refuseAndClose = ServerTcpMessage.Refuse | ServerTcpMessage.Close;
            TcpClientStream.WriteByte((byte)refuseAndClose);
            TcpClientStream.Flush();
            
            // Close the client
            Close();
        }

        public void Close()
        {
            IsRunning = false;
            TcpClientThread.Interrupt();
            TcpClientStream.Dispose();
            TcpClient.Close();
        }
    }
}
