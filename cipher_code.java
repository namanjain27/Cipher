import java.util.Scanner;

class Encryption 
{
    protected String key;
    private String str;
    protected int r;
    private String sec;
    public String Cypher;

    public void input()
    {
        Scanner sc = new Scanner(System.in);
        str = sc.nextLine();
        sec = sc.nextLine();
        key = "";
        Cypher = "";
        r = 10;
        sc.close();
    }

	protected void Encrypt()
	{
		int [][] data=new int[r][r];
		int ptr=0;
		char temp;
		for(int i=0;i<r;i++)
		{
			for(int j=0;j<r;j++)
			{
				if(ptr<str.length())
				{
					temp=str.charAt(ptr);
					ptr=ptr+1;
				}
				else
				{
					temp='@';//to fill the empty spaces at the end
				}
				data[i][j]=temp;
			}
		}
        Mat_gen(data);
        int key2[] = new int[sec.length()];
        for(int i =0; i<sec.length(); i++)
        {
            key2[i] = (int)sec.charAt(i);
        }
        XOR(data, key2);
        print_garb(data);
        for(int i =0; i<sec.length(); i++)
        {
            key = key + sec.charAt(i);
        }
	}

    private void Mat_gen(int p[][])
    {
        char c;
        int []a = new int[r];
        for(int i = 0; i<r; i++)
        {
            a[i] = i;
        }
        int [][]Mat = new int[r][r];
        int temp =0;
        for(int i = 0; i<r; i++)
        {
            temp = (int)((Math.random())*(r-i));
            c = (char)(a[temp] + 48);
            key = key +c;
            for(int j = 0; j<r; j++)
            {
                if(j==a[temp])
                 Mat[i][j] = 1;
                else
                 Mat[i][j] = 0;
            }
            for(int j = temp +1; j<r; j++)
            {
                a[j-1] = a[j] ;
            }
        }
        Scramblerow(p, Mat);
    }

	protected void XOR(int [][]data,int []key)
	{
		int ptr=0;
		int [][]encrypted = new int[r][r];
		for(int i=0;i<r;i++)
		{
            for(int j =0; j<r; j++)
            {
			  ptr=ptr%(key.length);
			  encrypted[i][j]=data[i][j]^key[ptr];
              ptr++;
            }
		}
		for(int i = 0; i<r; i++)
        {
            for(int j = 0; j<r; j++)
            {
                data[i][j] = encrypted[i][j];
            }
        }
	}

    protected void Scramblerow (int [][]data,int[][]key)
    {
        int sum;
        int [][]scramble=new int[r][r];
        for(int i=0;i<r;i++)
        {
            for(int j=0;j<r;j++)
            {
                sum=0;
                for(int k=0;k<r;k++)
                {
                    sum=sum+(data[i][k]*key[k][j]);
                }
                scramble[i][j]=sum;
            }
        }
        for(int i =0; i<r; i++)
        {
            for(int j =0; j<r; j++)
            {
                data[i][j] = scramble[i][j];
            }
        }
    }

    private void print_garb(int [][]data)
    {
        int t = 0;
        double f =0;
        int g = (int)(8*Math.random());
        g =g +2;
        key = key + (char)(g+48);
        for(int q = 0; q < r*r; q++)
        {
         for(int i = 0; i<g-1; i++)
         {
            f = Math.random();
            f = 8*r*r*f;
            t = (int) f;
            Cypher = Cypher + Integer.toString(t) + " ";
         }
         t =  data[(q/r)][(q%r)];
        Cypher = Cypher + Integer.toString(t) + " ";
       }
    }

}

class Decrypt extends Encryption
{
    protected void decrypt()
    {
        int data[][] = new int [r][r];
        char c = key.charAt(r);
        int u = c-48;
        int y =0;
        int count = 0;
        int tmp =0;
        for(int i =0; i<Cypher.length(); i++)
        {
            if(y==(u-1))
            {
                while(i<Cypher.length() && Cypher.charAt(i)!=32)
                {
                    c = Cypher.charAt(i);
                    tmp = tmp*10 +(c-48);
                    i++;
                }
                data[count/r][count%r] = tmp;
                count++;
                y=0;
                i++;
                tmp=0;
            }
            if( i<Cypher.length() && Cypher.charAt(i)==32)
               y++;
        }
        De_Mat(data);
    }

    private void De_Mat(int data[][])
    {
        char c;
        int temp =0;
        int h[][] = new int[r][r];
        for(int i = 0; i<r; i++)
        {
           c = key.charAt(i);
           temp = c - 48;
           for(int j =0; j<r; j++)
           {
               if(j==temp)
                h[j][i] = 1;
               else 
                h[j][i] =0;
           }
        }
        int key2[] = new int[key.length()-11];
        for(int i =0; i<key.length()-11; i++)
        {
            key2[i] = (int)key.charAt(i+11);
        }
        XOR(data, key2);
        Scramblerow(data, h);
        System.out.println();
        System.out.println("Decrypted message :");
        for(int i = 0; i<r; i++)
        {
            for(int j=0; j<r; j++)
            {
                System.out.print((char)(data[i][j]));
            }
        }
    }
      
}

class ex1
{
     public static void main(String args[])
     {
          Decrypt decypher = new Decrypt();
          decypher.input();
          decypher.Encrypt();
          System.out.println("Your message : "+(decypher.Cypher));
          System.out.println("Your Key : "+decypher.key);
          decypher.decrypt();
     }
}