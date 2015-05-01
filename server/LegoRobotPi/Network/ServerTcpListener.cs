using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace LegoRobotPi
{
    public class ServerTcpListener
    {
        private TcpListener TcpListener;
        private IList<TcpClient> TcpClients;
        private int MaxConnection;

        public ServerTcpListener(IPEndPoint ipEndPoint, int maxConnection)
        {
            TcpListener = new TcpListener(ipEndPoint);
            TcpClients = new List<TcpClient>();
            MaxConnection = maxConnection;
        }

        public void Start()
        {
            TcpListener.BeginAcceptTcpClient(new AsyncCallback(NewTcpClientCallback), TcpListener);
        }

        public void NewTcpClientCallback(IAsyncResult asyncResult)
        {
            TcpClient pendingTcpClient = TcpListener.EndAcceptTcpClient(asyncResult);

            if (TcpClients.Count < MaxConnection)
            {
                // Accept the pending connection
                TcpClients.Add(pendingTcpClient);
            }
            else
            {

            }
        }

        public void Stop()
        {
            TcpListener.Stop();
        }
    }
}
