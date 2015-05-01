using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace LegoRobotPi.Network
{
    [Flags]
    public enum ServerTcpMessage
    {
        Accept = 0x01,    // 000001
        Start = 0x02,     // 000010
        Wait = 0x04,      // 000100
        Refuse = 0x08,    // 001000
        Close = 0x16,     // 010000
        KeepAlive = 0x32, // 100000

        Forward = 0x64,   // 000001000000 
        Backward = 0x128, // 000010000000
        Left = 0x256,     // 000100000000
        Right = 0x512,    // 001000000000
        StopGo = 0x1024,  // 010000000000
        StopTurn = 0x2048 // 100000000000
    }
}
