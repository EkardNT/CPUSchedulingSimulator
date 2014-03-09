namespace Analysis
{
	partial class GeneratorView
	{
		/// <summary>
		/// Required designer variable.
		/// </summary>
		private System.ComponentModel.IContainer components = null;

		/// <summary>
		/// Clean up any resources being used.
		/// </summary>
		/// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
		protected override void Dispose(bool disposing)
		{
			if (disposing && (components != null))
			{
				components.Dispose();
			}
			base.Dispose(disposing);
		}

		#region Windows Form Designer generated code

		/// <summary>
		/// Required method for Designer support - do not modify
		/// the contents of this method with the code editor.
		/// </summary>
		private void InitializeComponent()
		{
			System.ComponentModel.ComponentResourceManager resources = new System.ComponentModel.ComponentResourceManager(typeof(GeneratorView));
			this.generateButton = new System.Windows.Forms.Button();
			this.groupBox1 = new System.Windows.Forms.GroupBox();
			this.label2 = new System.Windows.Forms.Label();
			this.label1 = new System.Windows.Forms.Label();
			this.burstTimeStDevTextBox = new System.Windows.Forms.TextBox();
			this.burstTimeMeanTextBox = new System.Windows.Forms.TextBox();
			this.groupBox2 = new System.Windows.Forms.GroupBox();
			this.label3 = new System.Windows.Forms.Label();
			this.label4 = new System.Windows.Forms.Label();
			this.delayMaxTextBox = new System.Windows.Forms.TextBox();
			this.delayMinTextBox = new System.Windows.Forms.TextBox();
			this.groupBox3 = new System.Windows.Forms.GroupBox();
			this.label5 = new System.Windows.Forms.Label();
			this.label6 = new System.Windows.Forms.Label();
			this.priorityStDevTextBox = new System.Windows.Forms.TextBox();
			this.priorityMeanTextBox = new System.Windows.Forms.TextBox();
			this.groupBox4 = new System.Windows.Forms.GroupBox();
			this.label7 = new System.Windows.Forms.Label();
			this.label8 = new System.Windows.Forms.Label();
			this.memoryStDevTextBox = new System.Windows.Forms.TextBox();
			this.memoryMeanTextBox = new System.Windows.Forms.TextBox();
			this.fileNameFormatTextBox = new System.Windows.Forms.TextBox();
			this.textBox8 = new System.Windows.Forms.TextBox();
			this.label9 = new System.Windows.Forms.Label();
			this.batchCountTextBox = new System.Windows.Forms.TextBox();
			this.label10 = new System.Windows.Forms.Label();
			this.label11 = new System.Windows.Forms.Label();
			this.processCountTextBox = new System.Windows.Forms.TextBox();
			this.groupBox1.SuspendLayout();
			this.groupBox2.SuspendLayout();
			this.groupBox3.SuspendLayout();
			this.groupBox4.SuspendLayout();
			this.SuspendLayout();
			// 
			// generateButton
			// 
			this.generateButton.Location = new System.Drawing.Point(210, 361);
			this.generateButton.Name = "generateButton";
			this.generateButton.Size = new System.Drawing.Size(75, 23);
			this.generateButton.TabIndex = 0;
			this.generateButton.Text = "Generate";
			this.generateButton.UseVisualStyleBackColor = true;
			this.generateButton.Click += new System.EventHandler(this.generateButton_Click);
			// 
			// groupBox1
			// 
			this.groupBox1.Controls.Add(this.label2);
			this.groupBox1.Controls.Add(this.label1);
			this.groupBox1.Controls.Add(this.burstTimeStDevTextBox);
			this.groupBox1.Controls.Add(this.burstTimeMeanTextBox);
			this.groupBox1.Location = new System.Drawing.Point(11, 28);
			this.groupBox1.Name = "groupBox1";
			this.groupBox1.Size = new System.Drawing.Size(134, 82);
			this.groupBox1.TabIndex = 1;
			this.groupBox1.TabStop = false;
			this.groupBox1.Text = "Burst Time";
			// 
			// label2
			// 
			this.label2.AutoSize = true;
			this.label2.Location = new System.Drawing.Point(6, 49);
			this.label2.Name = "label2";
			this.label2.Size = new System.Drawing.Size(14, 13);
			this.label2.TabIndex = 3;
			this.label2.Text = "σ";
			// 
			// label1
			// 
			this.label1.AutoSize = true;
			this.label1.Location = new System.Drawing.Point(6, 22);
			this.label1.Name = "label1";
			this.label1.Size = new System.Drawing.Size(13, 13);
			this.label1.TabIndex = 2;
			this.label1.Text = "µ";
			// 
			// burstTimeStDevTextBox
			// 
			this.burstTimeStDevTextBox.Location = new System.Drawing.Point(26, 45);
			this.burstTimeStDevTextBox.Name = "burstTimeStDevTextBox";
			this.burstTimeStDevTextBox.Size = new System.Drawing.Size(91, 20);
			this.burstTimeStDevTextBox.TabIndex = 1;
			this.burstTimeStDevTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// burstTimeMeanTextBox
			// 
			this.burstTimeMeanTextBox.Location = new System.Drawing.Point(26, 19);
			this.burstTimeMeanTextBox.Name = "burstTimeMeanTextBox";
			this.burstTimeMeanTextBox.Size = new System.Drawing.Size(91, 20);
			this.burstTimeMeanTextBox.TabIndex = 0;
			this.burstTimeMeanTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// groupBox2
			// 
			this.groupBox2.Controls.Add(this.label3);
			this.groupBox2.Controls.Add(this.label4);
			this.groupBox2.Controls.Add(this.delayMaxTextBox);
			this.groupBox2.Controls.Add(this.delayMinTextBox);
			this.groupBox2.Location = new System.Drawing.Point(151, 28);
			this.groupBox2.Name = "groupBox2";
			this.groupBox2.Size = new System.Drawing.Size(134, 82);
			this.groupBox2.TabIndex = 4;
			this.groupBox2.TabStop = false;
			this.groupBox2.Text = "Delay";
			// 
			// label3
			// 
			this.label3.AutoSize = true;
			this.label3.Location = new System.Drawing.Point(6, 49);
			this.label3.Name = "label3";
			this.label3.Size = new System.Drawing.Size(26, 13);
			this.label3.TabIndex = 3;
			this.label3.Text = "max";
			// 
			// label4
			// 
			this.label4.AutoSize = true;
			this.label4.Location = new System.Drawing.Point(6, 22);
			this.label4.Name = "label4";
			this.label4.Size = new System.Drawing.Size(23, 13);
			this.label4.TabIndex = 2;
			this.label4.Text = "min";
			// 
			// delayMaxTextBox
			// 
			this.delayMaxTextBox.Location = new System.Drawing.Point(38, 45);
			this.delayMaxTextBox.Name = "delayMaxTextBox";
			this.delayMaxTextBox.Size = new System.Drawing.Size(79, 20);
			this.delayMaxTextBox.TabIndex = 1;
			this.delayMaxTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// delayMinTextBox
			// 
			this.delayMinTextBox.Location = new System.Drawing.Point(38, 19);
			this.delayMinTextBox.Name = "delayMinTextBox";
			this.delayMinTextBox.Size = new System.Drawing.Size(79, 20);
			this.delayMinTextBox.TabIndex = 0;
			this.delayMinTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// groupBox3
			// 
			this.groupBox3.Controls.Add(this.label5);
			this.groupBox3.Controls.Add(this.label6);
			this.groupBox3.Controls.Add(this.priorityStDevTextBox);
			this.groupBox3.Controls.Add(this.priorityMeanTextBox);
			this.groupBox3.Location = new System.Drawing.Point(11, 116);
			this.groupBox3.Name = "groupBox3";
			this.groupBox3.Size = new System.Drawing.Size(134, 82);
			this.groupBox3.TabIndex = 4;
			this.groupBox3.TabStop = false;
			this.groupBox3.Text = "Priority";
			// 
			// label5
			// 
			this.label5.AutoSize = true;
			this.label5.Location = new System.Drawing.Point(6, 49);
			this.label5.Name = "label5";
			this.label5.Size = new System.Drawing.Size(14, 13);
			this.label5.TabIndex = 3;
			this.label5.Text = "σ";
			// 
			// label6
			// 
			this.label6.AutoSize = true;
			this.label6.Location = new System.Drawing.Point(6, 22);
			this.label6.Name = "label6";
			this.label6.Size = new System.Drawing.Size(13, 13);
			this.label6.TabIndex = 2;
			this.label6.Text = "µ";
			// 
			// priorityStDevTextBox
			// 
			this.priorityStDevTextBox.Location = new System.Drawing.Point(26, 45);
			this.priorityStDevTextBox.Name = "priorityStDevTextBox";
			this.priorityStDevTextBox.Size = new System.Drawing.Size(91, 20);
			this.priorityStDevTextBox.TabIndex = 1;
			this.priorityStDevTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// priorityMeanTextBox
			// 
			this.priorityMeanTextBox.Location = new System.Drawing.Point(26, 19);
			this.priorityMeanTextBox.Name = "priorityMeanTextBox";
			this.priorityMeanTextBox.Size = new System.Drawing.Size(91, 20);
			this.priorityMeanTextBox.TabIndex = 0;
			this.priorityMeanTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// groupBox4
			// 
			this.groupBox4.Controls.Add(this.label7);
			this.groupBox4.Controls.Add(this.label8);
			this.groupBox4.Controls.Add(this.memoryStDevTextBox);
			this.groupBox4.Controls.Add(this.memoryMeanTextBox);
			this.groupBox4.Location = new System.Drawing.Point(151, 116);
			this.groupBox4.Name = "groupBox4";
			this.groupBox4.Size = new System.Drawing.Size(134, 82);
			this.groupBox4.TabIndex = 4;
			this.groupBox4.TabStop = false;
			this.groupBox4.Text = "Memory";
			// 
			// label7
			// 
			this.label7.AutoSize = true;
			this.label7.Location = new System.Drawing.Point(6, 49);
			this.label7.Name = "label7";
			this.label7.Size = new System.Drawing.Size(14, 13);
			this.label7.TabIndex = 3;
			this.label7.Text = "σ";
			// 
			// label8
			// 
			this.label8.AutoSize = true;
			this.label8.Location = new System.Drawing.Point(6, 22);
			this.label8.Name = "label8";
			this.label8.Size = new System.Drawing.Size(13, 13);
			this.label8.TabIndex = 2;
			this.label8.Text = "µ";
			// 
			// memoryStDevTextBox
			// 
			this.memoryStDevTextBox.Location = new System.Drawing.Point(26, 45);
			this.memoryStDevTextBox.Name = "memoryStDevTextBox";
			this.memoryStDevTextBox.Size = new System.Drawing.Size(91, 20);
			this.memoryStDevTextBox.TabIndex = 1;
			this.memoryStDevTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// memoryMeanTextBox
			// 
			this.memoryMeanTextBox.Location = new System.Drawing.Point(26, 19);
			this.memoryMeanTextBox.Name = "memoryMeanTextBox";
			this.memoryMeanTextBox.Size = new System.Drawing.Size(91, 20);
			this.memoryMeanTextBox.TabIndex = 0;
			this.memoryMeanTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// fileNameFormatTextBox
			// 
			this.fileNameFormatTextBox.Location = new System.Drawing.Point(11, 311);
			this.fileNameFormatTextBox.Name = "fileNameFormatTextBox";
			this.fileNameFormatTextBox.Size = new System.Drawing.Size(274, 20);
			this.fileNameFormatTextBox.TabIndex = 5;
			this.fileNameFormatTextBox.Text = "batch{i}-b{b}-p{p}-m{m}.dat";
			this.fileNameFormatTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// textBox8
			// 
			this.textBox8.Location = new System.Drawing.Point(11, 204);
			this.textBox8.Multiline = true;
			this.textBox8.Name = "textBox8";
			this.textBox8.ReadOnly = true;
			this.textBox8.Size = new System.Drawing.Size(274, 101);
			this.textBox8.TabIndex = 6;
			this.textBox8.Text = resources.GetString("textBox8.Text");
			// 
			// label9
			// 
			this.label9.AutoSize = true;
			this.label9.Location = new System.Drawing.Point(8, 340);
			this.label9.Name = "label9";
			this.label9.Size = new System.Drawing.Size(66, 13);
			this.label9.TabIndex = 7;
			this.label9.Text = "Batch Count";
			// 
			// batchCountTextBox
			// 
			this.batchCountTextBox.Location = new System.Drawing.Point(120, 337);
			this.batchCountTextBox.Name = "batchCountTextBox";
			this.batchCountTextBox.Size = new System.Drawing.Size(84, 20);
			this.batchCountTextBox.TabIndex = 8;
			this.batchCountTextBox.Text = "10";
			this.batchCountTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// label10
			// 
			this.label10.AutoSize = true;
			this.label10.Location = new System.Drawing.Point(62, 9);
			this.label10.Name = "label10";
			this.label10.Size = new System.Drawing.Size(168, 13);
			this.label10.TabIndex = 9;
			this.label10.Text = "µ = Mean, σ = Standard Deviation";
			// 
			// label11
			// 
			this.label11.AutoSize = true;
			this.label11.Location = new System.Drawing.Point(8, 366);
			this.label11.Name = "label11";
			this.label11.Size = new System.Drawing.Size(106, 13);
			this.label11.TabIndex = 10;
			this.label11.Text = "Processes Per Batch";
			// 
			// processCountTextBox
			// 
			this.processCountTextBox.Location = new System.Drawing.Point(120, 363);
			this.processCountTextBox.Name = "processCountTextBox";
			this.processCountTextBox.Size = new System.Drawing.Size(84, 20);
			this.processCountTextBox.TabIndex = 11;
			this.processCountTextBox.Text = "10";
			this.processCountTextBox.TextAlign = System.Windows.Forms.HorizontalAlignment.Right;
			// 
			// GeneratorView
			// 
			this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
			this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
			this.ClientSize = new System.Drawing.Size(297, 393);
			this.Controls.Add(this.processCountTextBox);
			this.Controls.Add(this.label11);
			this.Controls.Add(this.label10);
			this.Controls.Add(this.batchCountTextBox);
			this.Controls.Add(this.label9);
			this.Controls.Add(this.textBox8);
			this.Controls.Add(this.fileNameFormatTextBox);
			this.Controls.Add(this.groupBox4);
			this.Controls.Add(this.groupBox3);
			this.Controls.Add(this.groupBox2);
			this.Controls.Add(this.groupBox1);
			this.Controls.Add(this.generateButton);
			this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
			this.Icon = ((System.Drawing.Icon)(resources.GetObject("$this.Icon")));
			this.MaximizeBox = false;
			this.MinimizeBox = false;
			this.Name = "GeneratorView";
			this.Text = "Generator";
			this.groupBox1.ResumeLayout(false);
			this.groupBox1.PerformLayout();
			this.groupBox2.ResumeLayout(false);
			this.groupBox2.PerformLayout();
			this.groupBox3.ResumeLayout(false);
			this.groupBox3.PerformLayout();
			this.groupBox4.ResumeLayout(false);
			this.groupBox4.PerformLayout();
			this.ResumeLayout(false);
			this.PerformLayout();

		}

		#endregion

		private System.Windows.Forms.Button generateButton;
		private System.Windows.Forms.GroupBox groupBox1;
		private System.Windows.Forms.Label label2;
		private System.Windows.Forms.Label label1;
		private System.Windows.Forms.TextBox burstTimeStDevTextBox;
		private System.Windows.Forms.TextBox burstTimeMeanTextBox;
		private System.Windows.Forms.GroupBox groupBox2;
		private System.Windows.Forms.Label label3;
		private System.Windows.Forms.Label label4;
		private System.Windows.Forms.TextBox delayMaxTextBox;
		private System.Windows.Forms.TextBox delayMinTextBox;
		private System.Windows.Forms.GroupBox groupBox3;
		private System.Windows.Forms.Label label5;
		private System.Windows.Forms.Label label6;
		private System.Windows.Forms.TextBox priorityStDevTextBox;
		private System.Windows.Forms.TextBox priorityMeanTextBox;
		private System.Windows.Forms.GroupBox groupBox4;
		private System.Windows.Forms.Label label7;
		private System.Windows.Forms.Label label8;
		private System.Windows.Forms.TextBox memoryStDevTextBox;
		private System.Windows.Forms.TextBox memoryMeanTextBox;
		private System.Windows.Forms.TextBox fileNameFormatTextBox;
		private System.Windows.Forms.TextBox textBox8;
		private System.Windows.Forms.Label label9;
		private System.Windows.Forms.TextBox batchCountTextBox;
		private System.Windows.Forms.Label label10;
		private System.Windows.Forms.Label label11;
		private System.Windows.Forms.TextBox processCountTextBox;
	}
}

